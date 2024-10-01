package com.cmj.app.domain.repository;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import com.cmj.app.domain.post.repository.PostRepository;
import com.cmj.app.domain.post.service.PostService;
import com.cmj.app.domain.post.service.ViewCountService;
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
public class PostServiceTest {

    private final PostService postService;

    @BeforeEach
    public void setUp() {


    }


    @Test
    public void test() {
        PostProjection test1 = postService.increaseAndGetViewCountAndFindPostWithMemberAndBoardById(1L, "test1");

        //test1의 조회수는 1이어야 한다.
        assert test1.getViewCount() == 1;
    }


}
