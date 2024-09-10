package com.cmj.security.domain.member.dto;

import com.cmj.security.domain.member.entity.Member;
import lombok.Builder;

@Builder
public record MemberResponse(
        Long id,
        String username,
        String name,
        String email,
        String phone,
        String address,
        String roles
) {

    public static MemberResponse of(Long id, String username, String name, String email, String phone, String address, String roles) {
        return MemberResponse.builder()
                .id(id)
                .username(username)
                .name(name)
                .email(email)
                .phone(phone)
                .address(address)
                .roles(roles)
                .build();
    }

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .address(member.getAddress())
                .roles(member.getRole().getRoleName())
                .build();
    }
}
