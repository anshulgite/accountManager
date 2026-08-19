package com.accountManager.common;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimitConfig {

    private final RedisTemplate<String, String> redisTemplate;

    public RateLimitConfig(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final int maxRequests = 5;
    private static final int timeWindow = 1;

    public boolean isAllowed(String ipAddress) {
        String key = "rate_limit:" + ipAddress;
        Long count = redisTemplate.opsForValue().increment(key, 1);
        if (count == 1) {
            redisTemplate.expire(key, timeWindow, TimeUnit.MINUTES);
        }

        return count <= maxRequests;
    }

    public long getRemainingAttempts(String ipAddress){
        String key = "rate_limit:" + ipAddress;
        String countStr = redisTemplate.opsForValue().get(key);
        long count = countStr != null ? Long.parseLong(countStr) : 0L;
        return Math.max(maxRequests - count,0);
    }

}
