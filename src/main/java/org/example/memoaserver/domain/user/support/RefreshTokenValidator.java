package org.example.memoaserver.domain.user.support;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.exception.TokenCategoryNotFoundException;
import org.example.memoaserver.global.exception.TokenExpiredException;
import org.example.memoaserver.global.exception.TokenInvalidException;
import org.example.memoaserver.global.exception.TokenNotFoundException;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenValidator {
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    public void validate(String device, String token) {
        checkNullToken(token);
        checkExpiredToken(token);
        checkTokenCategory(token);
        checkInvalidToken(token);
        checkDeviceMatch(device, token);
    }

    private void checkNullToken(String token) {
        if (token == null) {
            throw new TokenNotFoundException();
        }
    }

    private void checkInvalidToken(String token) {
        try {
            jwtUtil.getEmail(token);
        } catch (Exception e) {
            throw new TokenInvalidException();
        }
    }

    private void checkExpiredToken(String token) {
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException();
        }
    }

    private void checkTokenCategory(String token) {
        if (!jwtUtil.getCategory(token).equals("refresh")) {
            throw new TokenCategoryNotFoundException();
        }
    }

    private void checkDeviceMatch(String device, String token) {
        if (!token.equals(redisService.getOnRedisForToken(device + "::" + jwtUtil.getEmail(token)))) {
            throw new TokenNotFoundException();
        }
    }
}
