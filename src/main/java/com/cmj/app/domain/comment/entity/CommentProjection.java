package com.cmj.app.domain.comment.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
public record CommentProjection(Long id, String content, String username, Long memberId, Long postId, Long parentId,
                                LocalDateTime createdDate, LocalDateTime modifiedDate) {

    @QueryProjection
    public CommentProjection {
    }

}
