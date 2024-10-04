package com.cmj.app.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentRequest {

    private String content;
    private Long commentId;
    private Long parentId;
    private Long memberId;
    private Long postId;

}
