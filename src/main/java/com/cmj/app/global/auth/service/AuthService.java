package com.cmj.app.global.auth.service;

import com.cmj.app.global.auth.dto.SignUpRequest;
import com.cmj.app.global.auth.dto.SignUpResponse;
import com.cmj.app.global.auth.exception.UserAuthException;
import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Value("${rsa.public-key}")
    private String publicKey;

    @Transactional
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        if (memberService.existsByUsername(signUpRequest.username())) {
            throw new UserAuthException("이미 사용중인 아이디입니다.");
        }

        Member member = memberService.save(SignUpRequest.toEntity(signUpRequest, passwordEncoder));
        return SignUpResponse.of(member.getId(), member.getUsername(), member.getEmail(), member.getRole());
    }

    public String getPublicKey() {
        return publicKey;
    }

    public boolean isAlreadyLoggedIn(HttpServletRequest request) {
        return request.getUserPrincipal() != null;
    }


}
