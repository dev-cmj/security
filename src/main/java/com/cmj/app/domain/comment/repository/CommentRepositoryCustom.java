package com.cmj.app.domain.comment.repository;

import com.cmj.app.domain.comment.dto.CommentSearchCondition;
import com.cmj.app.domain.comment.entity.CommentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

public interface CommentRepositoryCustom {

    Page<CommentProjection> findCommentsPage(CommentSearchCondition condition);
    Slice<CommentProjection> findCommentsSlice(CommentSearchCondition condition);
}
