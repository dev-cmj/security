package com.cmj.app.global.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean(name = "cacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(conf)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
        conf.setHostName(this.host);
        conf.setPort(this.port);
        return new LettuceConnectionFactory(conf);
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    @Bean
    public CacheErrorHandler errorHandler() {
        return new CustomCacheErrorHandler();
    }

// Redis에 JWT 토큰을 저장할 경우, 토큰의 만료 시간을 설정하고, 토큰이 만료되었을 때 자동으로 삭제되도록 설정하는 것이 좋다.
// 이렇게 하면 Redis에 만료된 토큰이 남아있지 않아 불필요한 메모리 사용을 방지할 수 있다.
// 단, 로그아웃 기능이 구현된 경우, 로그아웃 시 Refresh Token을 Black List에 저장하여 해당 토큰이 이후에 재사용되지 않도록 관리할 수 있다.
// Black List에 저장된 토큰은 만료 시간과 관계없이 유효하지 않은 상태로 간주되며, 로그아웃 시 명시적으로 처리해야 한다.
// 이를 통해 사용자가 로그아웃한 후에도 해당 토큰을 이용한 인증 시도가 무효화되도록 한다.
}