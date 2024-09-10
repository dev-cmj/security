package com.cmj.security.domain.member.dto;

import lombok.Builder;

@Builder
public record MemberRequest(

        String username,
        String password,
        String name,
        String email,
        String phone,
        String address
) {
}
