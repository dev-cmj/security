package com.cmj.security.dto;

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
