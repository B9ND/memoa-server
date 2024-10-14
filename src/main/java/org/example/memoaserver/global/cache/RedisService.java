package org.example.memoaserver.global.cache;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisService {
    private final RedisTemplate<String, Object> authCodeRedisTemplate;
    private final RedisTemplate<String, Object> authenticEmailredisTemplate;


    public RedisService(
            @Qualifier("redisTemplate1") RedisTemplate<String, Object> authCodeRedisTemplate,
            @Qualifier("redisTemplate2") RedisTemplate<String, Object> authenticEmailredisTemplate) {
        this.authCodeRedisTemplate = authCodeRedisTemplate;
        this.authenticEmailredisTemplate = authenticEmailredisTemplate;
    }

    public void setOnARedisForAuthCode(String email, String authCode, long ttl) {
        authCodeRedisTemplate.opsForValue().set(email, authCode, ttl, TimeUnit.MINUTES);
    }

    public String getOnARedisForAuthCode(String email) {
        return (String) authCodeRedisTemplate.opsForValue().get(email);
    }

    public void setOnRedisForAuthenticEmail(String email, long ttl) {
        authenticEmailredisTemplate.opsForValue().set(email, email, ttl, TimeUnit.MINUTES);
    }

    public void deleteOnRedisForAuthenticEmail(String email) {
        authenticEmailredisTemplate.delete(email);
    }
}
