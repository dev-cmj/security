package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.repository.PostRepository;
import com.cmj.app.global.config.redis.RedisConfig;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(RedisConfig.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class ViewCountServiceTest {

    @MockBean
    private final RedisTemplate<String, String> redisTemplate;

    @MockBean
    private final PostRepository postRepository;

    private final ViewCountService viewCountService;

    private final Post post = Post
            .builder()
            .id(1L)
            .title("title")
            .content("test")
            .board(null)
            .member(null)
            .build();


    /*
    이 테스트는 Redis 가 정상적으로 동작할 때의 `increaseViewCount` 메서드를 테스트합니다.
    `viewCountService.increaseViewCount` 메서드를 호출하며
    이 메서드 내에서 사용하는 `redisTemplate.execute()` 및 `postRepository.increaseViewCount()`는 Mock 으로 처리되어 있습니다.
    Redis 가 정상적으로 동작할 때는 `PostRepository(DB)`에 조회수를 저장하는 동작을 하지 않고, `Redis`에서만 조회수를 관리한다는 것을 보장합니다.
     */
    @Test
    public void testIncreaseViewCountWhenRedisIsAvailable() {
        // Given
        String username = "user1";

        RedisOperations redisOperations = mock(RedisOperations.class);
        when(redisTemplate.execute(any(SessionCallback.class))).thenReturn(null);

        // When
        viewCountService.increaseViewCountInRedis(post.getId(), username);

        // Then
        verify(redisTemplate, times(1)).execute(any(SessionCallback.class));
        verify(postRepository, never()).increaseViewCount(anyLong());
    }

    /*
    이 테스트는 Redis 가 정상적으로 동작하지 않을 때의 `increaseViewCount` 메서드를 테스트합니다.
    `viewCountService.increaseViewCount` 메서드를 호출하며,
    이 메서드 내에서 사용하는 `redisTemplate.execute()`는 Redis 장애를 테스트 하여 예외를 발생시키고, `postRepository.increaseViewCount()`는 Mock 으로 처리되어 있습니다.
    Redis 가 정상적으로 동작하지 않을 때는 `PostRepository(DB)`에 조회수를 저장하는 동작을 하고, `Redis`는 조회수를 관리하지 못한다는 것을 보장합니다.
    */

    @Test
    public void testIncreaseViewCountFallbackWhenRedisFails() {
        // Given
        String username = "user1";

        // Simulate Redis failure
        when(redisTemplate.execute(any(SessionCallback.class))).thenThrow(new RuntimeException("Redis failure"));

        // When
        viewCountService.getViewCountFromRedis(post.getId());

        // Then
        // Verify that Redis was tried and failed, then DB was called
        verify(redisTemplate, times(1)).execute(any(SessionCallback.class));
        verify(postRepository, times(1)).increaseViewCount(post.getId());
    }

    /*
     DB 조회 없이 Redis 에서 조회수를 가져오는지 테스트합니다.
     */
    @Test
    public void testGetViewCountFromRedis() {
        // Given
        Long postId = 1L;
        String redisKey = "post:viewcount:" + postId;

        // Mock ValueOperations
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(redisKey)).thenReturn("5");

        // When
        Long viewCount = viewCountService.getViewCountFromRedis(postId);

        // Then
        assertEquals(Long.valueOf(5), viewCount);
        verify(postRepository, never()).findById(postId); // DB 조회는 하지 않음
    }

    /*
     Redis 에 조회수가 없을 때 DB 조회를 통해 조회수를 가져오는지 테스트합니다.
     */
    @Test
    public void testGetViewCountFromDBWhenRedisIsEmpty() {
        // Given
        Long postId = 1L;
        String redisKey = "post:viewcount:" + postId;

        // Mock ValueOperations
        ValueOperations valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(redisKey)).thenReturn(null); // Redis에 값이 없을 때

        // Mock DB 동작
        post.setViewCount(10L);
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // When
        Long viewCount = viewCountService.getViewCountFromRedis(postId);

        // Then
        assertEquals(Long.valueOf(10), viewCount); // DB의 조회수를 반환
        verify(postRepository, times(1)).findById(postId); // DB 조회 수행
    }

}