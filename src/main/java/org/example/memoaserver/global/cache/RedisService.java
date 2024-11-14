package org.example.memoaserver.global.cache;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisService {
    private final RedisTemplate<String, Object> tokenRedisTemplate;
    private final RedisTemplate<String, Object> authCodeRedisTemplate;
    private final RedisTemplate<String, Object> authenticEmailredisTemplate;

    public RedisService(
            @Qualifier("redisTemplate0") RedisTemplate<String, Object> tokenRedisTemplate,
            @Qualifier("redisTemplate1") RedisTemplate<String, Object> authCodeRedisTemplate,
            @Qualifier("redisTemplate2") RedisTemplate<String, Object> authenticEmailredisTemplate) {
        this.tokenRedisTemplate = tokenRedisTemplate;
        this.authCodeRedisTemplate = authCodeRedisTemplate;
        this.authenticEmailredisTemplate = authenticEmailredisTemplate;
    }

    public void deleteOnRedisForToken(String key) {
        tokenRedisTemplate.delete(key);
    }

    @Transactional
    public void saveToken(String key, String value, Long expired) {
        if (findOnRedisForToken(key)) {
            deleteOnRedisForToken(key);
        }
        tokenRedisTemplate.opsForValue().set(key, value, expired, TimeUnit.MILLISECONDS);
    }

    public String getOnRedisForToken(String key) {
        return (String) tokenRedisTemplate.opsForValue().get(key);
    }

    public Boolean findOnRedisForToken(String key) {
        return tokenRedisTemplate.hasKey(key);
    }

    public void setOnRedisForAuthCode(String email, String authCode, long ttl) {
        authCodeRedisTemplate.opsForValue().set(email, authCode, ttl, TimeUnit.MINUTES);
    }

    public String getOnRedisForAuthCode(String email) {
        return (String) authCodeRedisTemplate.opsForValue().get(email);
    }

    public Boolean findOnRedisForAuthenticEmail(String email) {
        return authenticEmailredisTemplate.hasKey(email);
    }

    public void setOnRedisForAuthenticEmail(String email, long ttl) {
        authenticEmailredisTemplate.opsForValue().set(email, email, ttl, TimeUnit.MINUTES);
    }

    public void deleteOnRedisForAuthenticEmail(String email) {
        authenticEmailredisTemplate.delete(email);
    }
}
