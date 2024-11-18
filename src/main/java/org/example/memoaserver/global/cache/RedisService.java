package org.example.memoaserver.global.cache;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisService {
    private final RedisTemplate<String, Object> tokenRedis;
    private final RedisTemplate<String, Object> authCodeRedis;
    private final RedisTemplate<String, Object> authenticEmailRedis;
    private final RedisTemplate<String, Object> rateLimiterRedis;

    public RedisService(
            @Qualifier("tokenRedisTemplate") RedisTemplate<String, Object> tokenRedis,
            @Qualifier("authCodeRedisTemplate") RedisTemplate<String, Object> codeRedis,
            @Qualifier("authenticEmailRedisTemplate") RedisTemplate<String, Object> authEmailRedis,
            @Qualifier("rateLimiterRedisTemplate") RedisTemplate<String, Object> rateLimiterRedis) {
        this.tokenRedis = tokenRedis;
        this.authCodeRedis = codeRedis;
        this.authenticEmailRedis = authEmailRedis;
        this.rateLimiterRedis = rateLimiterRedis;
    }

    public void deleteOnRedisForToken(String key) {
        tokenRedis.delete(key);
    }

    @Transactional
    public void saveToken(String key, String value, Long expired) {
        if (findOnRedisForToken(key)) {
            deleteOnRedisForToken(key);
        }
        tokenRedis.opsForValue().set(key, value, expired, TimeUnit.MILLISECONDS);
    }

    public String getOnRedisForToken(String key) {
        return (String) tokenRedis.opsForValue().get(key);
    }

    public Boolean findOnRedisForToken(String key) {
        return tokenRedis.hasKey(key);
    }

    public void setOnRedisForAuthCode(String email, String authCode, long ttl) {
        authCodeRedis.opsForValue().set(email, authCode, ttl, TimeUnit.MINUTES);
    }

    public String getOnRedisForAuthCode(String email) {
        return (String) authCodeRedis.opsForValue().get(email);
    }

    public Boolean findOnRedisForAuthenticEmail(String email) {
        return authenticEmailRedis.hasKey(email);
    }

    public void setOnRedisForAuthenticEmail(String email, long ttl) {
        authenticEmailRedis.opsForValue().set(email, email, ttl, TimeUnit.MINUTES);
    }

    public void deleteOnRedisForAuthenticEmail(String email) {
        authenticEmailRedis.delete(email);
    }

    public Long incrementRedisForRateLimiter(String clientIp) {
        return rateLimiterRedis.opsForValue().increment("req_count::" + clientIp);
    }

    public Boolean ExpiredForRateLimiter(String clientIp) {
        return rateLimiterRedis.expire("req_count::" + clientIp, 25, TimeUnit.SECONDS);
    }
}
