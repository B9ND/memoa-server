package org.example.memoaserver.global.security.jwt.service;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_PREFIX = "refresh_token:";
    private static final String INVERSE_INDEX_PREFIX = "refresh_to_phoneNumber:";
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return new ResponseEntity<>("can't find all of the cookies", HttpStatus.UNAUTHORIZED);
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
                break;
            }
        }

        if (refresh == null) {
            return new ResponseEntity<>("refresh cookie not found", HttpStatus.UNAUTHORIZED);
        }

        try {
            jwtUtil.getUsername(refresh);
        } catch (Exception e) {
            return new ResponseEntity<>("unknown token", HttpStatus.UNAUTHORIZED);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        String category = jwtUtil.getCategory(refresh);

        if (!existsByRefreshToken(refresh)) {
            return new ResponseEntity<>("refresh token not found", HttpStatus.NOT_FOUND);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        String newAccess = jwtUtil.createJWT("access", username, role, jwtProperties.getAccess().getExpiration());
        String newRefresh = jwtUtil.createJWT("refresh", username, role, jwtProperties.getRefresh().getExpiration());

        deleteByRefreshToken(refresh);
        addRefreshEntity(username, newRefresh, jwtProperties.getRefresh().getExpiration());

        response.setHeader("Authorization", newAccess);
        response.addCookie(createCookie("refresh", newRefresh, jwtProperties.getRefresh().getExpiration()));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    public Cookie createCookie(String key, String value, Long maxAge) {
        Cookie cookie = new Cookie(key, value);
        int maxAgeInt = maxAge.intValue();
        cookie.setMaxAge(maxAgeInt);
        // cookie.setSecure(true);
        // cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }

    public String findByPhoneNumber(String username) {
        String key = TOKEN_PREFIX + username;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void addRefreshEntity(String username, String refresh, Long expiredMs) {
        String key = TOKEN_PREFIX + username;
        String refreshKey = INVERSE_INDEX_PREFIX + refresh;

        if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
            deleteByRefreshToken(INVERSE_INDEX_PREFIX + findByPhoneNumber(username));
        }

        redisTemplate.opsForValue().set(key, refresh, expiredMs, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().set(refreshKey, username, expiredMs, TimeUnit.MILLISECONDS);
    }

    public void deleteByRefreshToken(String refresh) {
        String username = findByRefreshToken(refresh.replaceFirst(INVERSE_INDEX_PREFIX, ""));
        redisTemplate.delete(TOKEN_PREFIX + username);
        redisTemplate.delete(INVERSE_INDEX_PREFIX + refresh.replaceFirst(INVERSE_INDEX_PREFIX, ""));
    }

    public String findByRefreshToken(String refresh) {
        String key = INVERSE_INDEX_PREFIX + refresh;
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Boolean existsByRefreshToken(String refresh) {
        String key = INVERSE_INDEX_PREFIX + refresh;
        return redisTemplate.hasKey(key);
    }
}
