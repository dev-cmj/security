package com.cmj.app.domain.repository;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.repository.PostRepository;
import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.domain.post.service.PostViewService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestConstructor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class PostRepositoryTest {

    private final PostRepository postRepository;
    private final PostViewService postViewService;
    private final RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void setUp() {
        //redis 초기화
        redisTemplate.delete("post:viewcount:2");

        for (int i = 0; i < 100; i++) {
            redisTemplate.delete("user:viewed:post:2:" + i);
        }

    }


    @Test
    public void testConcurrentUpdateViewCount() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        Post post = postRepository.findPostWithMemberAndBoardById(2L).orElseThrow();
        Long postId = post.getId();


        for (int i = 0; i < numberOfThreads; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    postViewService.increaseViewCount(post, String.valueOf(finalI));
                } catch (Exception e) {
                    System.out.println("조회수 증가 실패: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(20, TimeUnit.SECONDS);
        executorService.shutdown();

        // 최종 조회수 확인
        Long viewCount = postViewService.getViewCount(postId);
        System.out.println("최종 조회수: " + viewCount);
    }

}
