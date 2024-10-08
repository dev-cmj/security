package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.repository.PostRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewCountService {

    private static final String REDIS_KEY_PREFIX = "post:view:";

    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;


    @Retry(name = "viewCount", fallbackMethod = "increaseViewCountInDb")
    @CircuitBreaker(name = "viewCount", fallbackMethod = "increaseViewCountInDb")
    public void increaseViewCountInRedis(Long postId, String userId) {
        String key = REDIS_KEY_PREFIX + postId;
        redisTemplate.opsForHyperLogLog().add(key, userId);
        redisTemplate.expire(key, Duration.ofDays(1));  // 1일 후 자동 만료
    }

    public void increaseViewCountInDb(Long postId, String userId, Throwable throwable) {
        log.error("increaseViewCountInDb: postId={}, userId={}, throwable={}", postId, userId, throwable.getMessage());
        postRepository.increaseViewCount(postId);
    }

    @CircuitBreaker(name = "viewCount", fallbackMethod = "getViewCountFromDb")
    public Long getViewCountFromRedis(Long postId) {
        String key = REDIS_KEY_PREFIX + postId;
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    public Long getViewCountFromDb(Long postId, Throwable throwable) {
        log.error("getViewCountFromDb: postId={}, throwable={}", postId, throwable.getMessage());
        return postRepository.findViewCountById(postId)
                .map(PostProjection::getViewCount)
                .orElse(0L);
    }

}
