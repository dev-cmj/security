package com.cmj.app.global.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisHealthCheckService {

    private final RedisConnectionFactory redisConnectionFactory;

    @Cacheable(value = "redisStatus", cacheManager = "cacheManager", sync = true)
    public boolean isRedisEnabled() {
        try (RedisConnection connection = redisConnectionFactory.getConnection()) {
            return connection.ping() != null;
        } catch (Exception e) {
            return false;
        }
    }
}