package com.cmj.app.domain.post.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.post.entity.Post;

public record PostRequest(
        String title,
        String content,
        String author
) {


    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }


}
