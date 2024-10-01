package com.cmj.app.domain.post.repository;


import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface PostRepositoryCustom {
    Optional<Post> findPostByIdWithEntity(Long postId);

    Optional<PostProjection> findPostByIdWithProjection(Long postId);

    Page<PostProjection> findPostsPage(PostSearchCondition condition);

    Slice<PostProjection> findPostsSlice(PostSearchCondition condition);

    void increaseViewCount(Long postId);

    Optional<PostProjection> findViewCountById(Long postId);
}
