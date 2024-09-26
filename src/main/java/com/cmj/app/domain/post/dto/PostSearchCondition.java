package com.cmj.app.domain.post.dto;

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
        String paginationType // slice, page
) {
}