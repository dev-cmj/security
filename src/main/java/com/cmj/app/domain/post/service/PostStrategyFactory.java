package com.cmj.app.domain.post.service;

import com.cmj.app.global.common.service.RedisHealthCheckService;
import org.springframework.stereotype.Component;

@Component
public class PostStrategyFactory {

    private final RedisPostStrategy redisPostStrategy;
    private final SimplePostStrategy simplePostStrategy;
    private final RedisHealthCheckService redisHealthCheckService;

    public PostStrategyFactory(RedisPostStrategy redisPostStrategy,
                               SimplePostStrategy simplePostStrategy,
                               RedisHealthCheckService redisHealthCheckService) {
        this.redisPostStrategy = redisPostStrategy;
        this.simplePostStrategy = simplePostStrategy;
        this.redisHealthCheckService = redisHealthCheckService;
    }

    public PostStrategy getStrategy() {
        if (redisHealthCheckService.isRedisEnabled()) {
            return redisPostStrategy;
        } else {
            return simplePostStrategy;
        }
    }
}