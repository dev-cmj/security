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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class ViewCountService {

    private final RedisTemplate<String, String> redisTemplate;
    private final PostRepository postRepository;

    // 사용자가 게시글 클릭 시 조회수 증가 로직
    @Transactional
    @CircuitBreaker(name = "postViewCount", fallbackMethod = "increaseViewCountFallback")
    public void increaseViewCount(Post post, String username) {
        String viewKey = "user:viewed:post:" + post.getId() + ":" + username;
        String redisKey = "post:viewcount:" + post.getId();

        SessionCallback<List<Object>> callback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) {
                Boolean hasViewed = operations.hasKey(viewKey);

                if (Boolean.FALSE.equals(hasViewed)) {
                    // Redis에 조회수 저장 시작
                    String viewCount = redisTemplate.opsForValue().get(redisKey);

                    // Redis에 없으면 DB 조회수로부터 시작
                    if (viewCount == null) {
                        Long dbViewCount = post.getViewCount();
                        redisTemplate.opsForValue().set(redisKey, String.valueOf(dbViewCount));
                    }

                    // 조회수 증가
                    operations.opsForValue().increment(redisKey);
                    operations.opsForValue().set(viewKey, "1", 30, TimeUnit.MINUTES); // 중복 방지
                }
                return null;
            }
        };

        redisTemplate.execute(callback);
    }

    // Fallback 메서드
    @Transactional
    public void increaseViewCountFallback(Post post, String username, Throwable throwable) {
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

    //Redis에 있는 조회수를 DB에 저장하는 스케줄러 로직, 같거나 더 많은 조회수가 있을 경우 DB에 저장
    @Transactional
    public void saveViewCountFromRedisToDB() {
        List<Post> postsToUpdate = new ArrayList<>();

        redisTemplate.keys("post:viewcount:*").forEach(key -> {
            String postId = key.split(":")[2];
            Long redisViewCount = Long.parseLong(redisTemplate.opsForValue().get(key));
            Optional<Post> post = postRepository.findById(Long.parseLong(postId));
            Long dbViewCount = post.map(Post::getViewCount).orElse(0L);

            if (redisViewCount >= dbViewCount) {
                post.ifPresent(p -> {
                    p.setViewCount(redisViewCount);  // 엔티티 필드 수정
                    postsToUpdate.add(p);  // 업데이트할 엔티티 목록에 추가
                });
            }
        });

        postRepository.saveAll(postsToUpdate);  // 배치 저장
    }
}