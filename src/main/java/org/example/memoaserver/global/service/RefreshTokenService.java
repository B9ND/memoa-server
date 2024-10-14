package org.example.memoaserver.global.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.JwtTokenDTO;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisService redisService;

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    private static final String TOKEN_PREFIX = "refresh_token:";
    private static final String INVERSE_INDEX_PREFIX = "refresh_to_email:";

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refresh = request.getHeader("Refresh");

        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.getEmail(refresh);
        } catch (Exception e) {
            return new ResponseEntity<>("unknown token", HttpStatus.UNAUTHORIZED);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!category.equals("refresh")) {

            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        if (!existsByRefreshToken(refresh)) {
            return new ResponseEntity<>("refresh token not found", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);
        Role role = Role.valueOf(jwtUtil.getRole(refresh));

        String newAccess = jwtUtil.createJwt("access", email, role, jwtProperties.getAccess().getExpiration());
        String newRefresh = jwtUtil.createJwt("refresh", email, role, jwtProperties.getRefresh().getExpiration());

        deleteByRefreshToken(refresh);
        addRefreshEntity(email, newRefresh, jwtProperties.getRefresh().getExpiration());

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO((newAccess), newRefresh);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), jwtTokenDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String refresh = request.getHeader("Refresh");

        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Boolean isExist = existsByRefreshToken(refresh);
        if (!isExist) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        deleteByRefreshToken(refresh);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void deleteByRefreshToken(String refresh) {
        String email = findByRefreshToken(refresh.replaceFirst(INVERSE_INDEX_PREFIX, ""));
        redisService.deleteOnRedisForToken(TOKEN_PREFIX + email);
        redisService.deleteOnRedisForToken(INVERSE_INDEX_PREFIX + refresh.replaceFirst(INVERSE_INDEX_PREFIX, ""));
    }

    public void addRefreshEntity(String email, String refresh, Long expiredMs) {
        String key = TOKEN_PREFIX + email;
        String refreshKey = INVERSE_INDEX_PREFIX + refresh;

        if (redisService.findOnRedisForToken(key)) {
            deleteByRefreshToken(INVERSE_INDEX_PREFIX + findByEmail(email));
        }

        redisService.setOnRedisForToken(key, refresh, expiredMs, TimeUnit.MILLISECONDS);
        redisService.setOnRedisForToken(refreshKey, email, expiredMs, TimeUnit.MILLISECONDS);
    }

    private String findByEmail(String email) {
        String key = TOKEN_PREFIX + email;
        return redisService.getOnRedisForToken(key);
    }

    private String findByRefreshToken(String refresh) {
        String key = INVERSE_INDEX_PREFIX + refresh;
        return redisService.getOnRedisForToken(key);
    }

    private Boolean existsByRefreshToken(String refresh) {
        String key = INVERSE_INDEX_PREFIX + refresh;
        return redisService.findOnRedisForToken(key);
    }
}