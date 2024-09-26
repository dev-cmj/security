package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.repository.PostProjection;
import com.cmj.app.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //CRUD operations
    @Transactional
    public long savePost(Post post) {
        return postRepository.save(post).getId();
    }


    public Post findPostById(long id) {
        return postRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void updatePostById(long id, Post post) {
        Post postToUpdate = postRepository.findById(id).orElseThrow();
        postToUpdate.update(post);
    }

    @Transactional
    public void deletePostById(long id) {
        postRepository.deleteById(id);
    }

    //조회수를 증가시킨다.
    public void increaseViewCount(Post post, String username) {
        SessionCallback<List<Object>> callback = new SessionCallback<>() {
            @Override
            public List<Object> execute(RedisOperations operations) {
                String viewKey = "user:viewed:post:" + post.getId() + ":" + username;
                String redisKey = "post:viewcount:" + post.getId();

                // 조회 기록이 있는지 확인
                Boolean hasViewed = operations.hasKey(viewKey);
                if (Boolean.FALSE.equals(hasViewed)) {
                    operations.opsForValue().increment(redisKey);
                    operations.opsForValue().set(viewKey, "1", 30, TimeUnit.MINUTES);
                }
                return null;
            }
        };

        redisTemplate.execute(callback);

        getViewCount(post.getId());
    }

    //조회수를 가져온다.
    public Long getViewCount(Long postId) {
        String redisKey = "post:viewcount:" + postId;
        String viewCount = redisTemplate.opsForValue().get(redisKey);
        return viewCount != null ? Long.parseLong(viewCount) : 0L;
    }

    public Page<PostProjection> findPostsPage(PostSearchCondition condition) {
        return postRepository.findPostsPage(condition);
    }

}
