package org.example.memoaserver.global.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private final RedisService redisService;

    public boolean isAllowed(String clientIp) {
        String key;
        return true;
    }
}
