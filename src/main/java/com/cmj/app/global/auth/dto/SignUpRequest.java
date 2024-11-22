package com.cmj.app.global.auth.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import com.cmj.app.global.encode.PasswordCryptService;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SignUpRequest(
        @NotEmpty(message = "아이디는 필수 입력 값입니다.")
        String username,
        @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
        String password,
        @NotEmpty(message = "이메일은 필수 입력 값입니다.")
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
