package com.cmj.app.domain.post.dto;

import com.cmj.app.global.domain.PaginationType;
import lombok.Builder;

@Builder
public record PostSearchCondition(
        String title,
        String content,
        String author,
        String comment,
        Integer page,
        Integer size,
        String order,
        String sortField,
        PaginationType paginationType
) {
}