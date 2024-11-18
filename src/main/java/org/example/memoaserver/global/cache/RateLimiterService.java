package org.example.memoaserver.global.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimiterService {
    private static final int MAX_REQUEST = 5000;
    private final RedisService redisService;

    public boolean isAllowed(String clientIp) {
        Long requestCount = redisService.incrementRedisForRateLimiter(clientIp);

        if (requestCount == 1) {
            redisService.ExpiredForRateLimiter(clientIp);
        }

        return requestCount <= MAX_REQUEST;
    }
}
