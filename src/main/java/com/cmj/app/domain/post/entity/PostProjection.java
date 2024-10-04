package com.cmj.app.domain.post.entity;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PostProjection {
    private Long id;
    private String title;
    private String content;
    @Setter
    private Long viewCount;
    private String author;
    private Long commentCount;
    private Long likeCount;

    @QueryProjection
    public PostProjection(Long id, String title, String content, Long viewCount, String author, Long commentCount, Long likeCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
        this.author = author;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    @QueryProjection
    public PostProjection(Long id, Long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }


}
