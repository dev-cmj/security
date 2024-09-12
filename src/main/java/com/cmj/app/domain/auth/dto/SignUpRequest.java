package com.cmj.app.domain.auth.dto;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
public record SignUpRequest(
        String username,
        String password,
        String email
) {

    // 회원가입 요청 Dto를 회원 엔티티로 변환
    public static Member toEntity(SignUpRequest signUpRequest, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .username(signUpRequest.username())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .email(signUpRequest.email())
                .role(MemberRole.USER)
                .build();
    }
}
