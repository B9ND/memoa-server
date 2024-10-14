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

    public JwtTokenDTO reissue(HttpServletRequest request) {
        String refresh = request.getHeader("Refresh");
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

        checkRefreshToken(device, refresh);

        String email = jwtUtil.getEmail(refresh);

        if (!refresh.equals(findByEmail(device, email))) {
            throw new RuntimeException("Refresh token expired");
        }

        Role role = Role.valueOf(jwtUtil.getRole(refresh));

        String newAccess = jwtUtil.createJwt("access", email, role, device, jwtProperties.getAccess().getExpiration());
        String newRefresh = jwtUtil.createJwt("refresh", email, role, device, jwtProperties.getRefresh().getExpiration());

        redisService.saveToken(device + "::" + email, newRefresh, jwtProperties.getRefresh().getExpiration());

        return JwtTokenDTO.builder()
                .access(newAccess)
                .refresh(newRefresh)
                .build();
    }

    public void logout(HttpServletRequest request) {
        String refresh = request.getHeader("Refresh");
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

        checkRefreshToken(device, refresh);

        deleteTokenByEmail(device, jwtUtil.getEmail(refresh));
    }

    private void checkRefreshToken(String device, String refresh) {
        if (refresh == null) {
            throw new RuntimeException("Refresh header is missing");
        }

        try {
            jwtUtil.getEmail(refresh);
        } catch (Exception e) {
            throw new RuntimeException("Refresh header is invalid");
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Refresh header is expired");
        }

        if (!jwtUtil.getCategory(refresh).equals("refresh")) {
            throw new RuntimeException("Refresh header is missing");
        }

        String email = jwtUtil.getEmail(refresh);

        if (!refresh.equals(findByEmail(device, email))) {
            throw new RuntimeException("Refresh header is missing");
        }
    }

    private String findByEmail(String device, String email) {
        return redisService.getOnRedisForToken(device + "::" + email);
    }

    private void deleteTokenByEmail(String device, String email) {
        redisService.deleteOnRedisForToken(device + "::" + email);
    }
}