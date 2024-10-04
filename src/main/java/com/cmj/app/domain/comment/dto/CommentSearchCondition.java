package com.cmj.app.domain.comment.dto;

import com.cmj.app.global.domain.PaginationType;
import lombok.Builder;

@Builder
public record CommentSearchCondition(
        String content,
        Long postId,
        Long memberId,
        Long parentId,
        Integer page,
        Integer size,
        String order,
        String sortField,
        PaginationType paginationType
) {
}
