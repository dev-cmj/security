package com.cmj.app.domain.post.repository;


import com.cmj.app.domain.post.dto.PostSearchCondition;
import com.cmj.app.domain.post.entity.Post;
import com.cmj.app.domain.post.entity.PostProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface PostRepositoryCustom {
    public Optional<Post> findPostWithMemberAndBoardById(Long postId);
    public Page<PostProjection> findPostsPage(PostSearchCondition condition);
    public Slice<PostProjection> findPostsSlice(PostSearchCondition condition);
}
