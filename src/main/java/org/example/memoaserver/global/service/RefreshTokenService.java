package org.example.memoaserver.global.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.encode.SHA256;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.JwtTokenDTO;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refresh = request.getHeader("Refresh");
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

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

        if (!jwtUtil.getCategory(refresh).equals("refresh")) {

            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String email = jwtUtil.getEmail(refresh);

        if (!refresh.equals(findByEmail(device, email))) {
             return new ResponseEntity<>("invalid refresh token2", HttpStatus.BAD_REQUEST);
        }

        Role role = Role.valueOf(jwtUtil.getRole(refresh));

        String newAccess = jwtUtil.createJwt("access", email, role, device, jwtProperties.getAccess().getExpiration());
        String newRefresh = jwtUtil.createJwt("refresh", email, role, device, jwtProperties.getRefresh().getExpiration());

        deleteTokenByEmail(email, device);

        redisService.saveToken(device + "::" + email, newRefresh, jwtProperties.getRefresh().getExpiration());

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO((newAccess), newRefresh);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), jwtTokenDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String refresh = request.getHeader("Refresh");
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

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

        if (jwtUtil.isExpired(refresh)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        deleteTokenByEmail(device, jwtUtil.getEmail(refresh));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String findByEmail(String device, String email) {
        return redisService.getOnRedisForToken(device + "::" + email);
    }

    private void deleteTokenByEmail(String device, String email) {
        redisService.deleteOnRedisForToken(device + "::" + email);
    }
}