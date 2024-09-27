package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostViewService {

    private final PostStrategyFactory postStrategyFactory;

    public void increaseViewCount(Post post, String username) {
        PostStrategy strategy = postStrategyFactory.getStrategy();
        strategy.increaseViewCount(post, username);
    }

    public Long getViewCount(Long postId) {
        PostStrategy strategy = postStrategyFactory.getStrategy();
        return strategy.getViewCount(postId);
    }
}
