package com.cmj.app.global.auth.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import com.cmj.app.global.encode.PasswordCryptService;
import lombok.Builder;

@Builder
public record SignUpRequest(
        String username,
        String password,
        String email
) {

    // 회원가입 요청 Dto를 회원 엔티티로 변환
    public static Member toEntity(SignUpRequest signUpRequest, PasswordCryptService passwordCryptService) {
        return Member.builder()
                .username(signUpRequest.username())
                .password(passwordCryptService.encrypt(signUpRequest.password()))
                .email(signUpRequest.email())
                .role(MemberRole.USER)
                .build();
    }
}
