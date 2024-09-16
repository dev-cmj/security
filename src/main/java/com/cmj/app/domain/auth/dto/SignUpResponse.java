package com.cmj.app.domain.auth.dto;

import com.cmj.app.domain.member.entity.MemberRole;
import lombok.Builder;

@Builder
public record SignUpResponse(
        Long memberId,
        String username,
        String email,
        MemberRole role
) {

    public static SignUpResponse of(Long memberId, String username, String email, MemberRole role) {
        return SignUpResponse.builder()
                .memberId(memberId)
                .username(username)
                .email(email)
                .role(role)
                .build();
    }


}
