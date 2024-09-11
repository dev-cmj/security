package com.cmj.app.domain.member.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import lombok.Builder;

@Builder
public record MemberReqDto(
    String username,
    String email,
    MemberRole role
) {

    //Req -> Entity
    public Member toEntity() {
        return Member.builder()
            .username(username)
            .email(email)
            .role(role)
            .build();
    }
}
