package com.cmj.app.domain.post.repository;


import com.cmj.app.domain.post.entity.Post;

import java.util.Optional;

public interface PostRepositoryCustom {
    public Optional<Post> findPostWithMemberAndBoardById(Long postId);
}
