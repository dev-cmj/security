package com.cmj.app.domain.post.repository;


import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostRepositoryCustom {
    public Optional<Post> findPostWithMemberAndBoardById(Long postId);
    public Page<PostProjection> findPostsPage(PostSearchCondition condition);
}
