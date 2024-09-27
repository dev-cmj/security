package com.cmj.app.domain.post.service;

import com.cmj.app.domain.post.entity.Post;

public interface PostStrategy {

    public void increaseViewCount(Post post, String username);

    public Long getViewCount(Long postId);
}
