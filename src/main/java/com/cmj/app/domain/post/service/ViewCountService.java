package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.repository.PostRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewCountService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;

    // 조회수를 증가시킨다. Redis가 장애 시 DB로 Fallback
    @CircuitBreaker(name = "postViewCount", fallbackMethod = "increaseViewCountFallback")
    @Transactional
    public void increaseViewCount(Post post, String username) {
        // Redis에 조회수 증가 로직
        SessionCallback<List<Object>> callback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) {
                String viewKey = "user:viewed:post:" + post.getId() + ":" + username;
                String redisKey = "post:viewcount:" + post.getId();

                Boolean hasViewed = operations.hasKey(viewKey);
                if (Boolean.FALSE.equals(hasViewed)) {
                    operations.opsForValue().increment(redisKey);
                    operations.opsForValue().set(viewKey, "1", 30, TimeUnit.MINUTES);
                }
                return null;
            }
        };

        redisTemplate.execute(callback);
    }

    // Fallback 메서드
    @Transactional
    public void increaseViewCountFallback(Post post, String username, Throwable throwable) {
        // Fallback: Redis 장애 시 DB로 조회수 증가 처리
        log.error("Redis 장애 발생, DB로 전환", throwable);
        postRepository.increaseViewCount(post.getId());
    }

    // 조회수를 가져온다
    public Long getViewCount(Long postId) {
        String redisKey = "post:viewcount:" + postId;
        String viewCount = redisTemplate.opsForValue().get(redisKey);
        if (viewCount != null) {
            return Long.parseLong(viewCount);
        } else {
            return postRepository.findById(postId)
                    .map(Post::getViewCount)
                    .orElse(0L);
        }
    }
}