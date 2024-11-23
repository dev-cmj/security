package com.cmj.app.global.auth.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.global.auth.dto.SignUpRequest;
import com.cmj.app.global.auth.dto.SignUpResponse;
import com.cmj.app.global.auth.exception.UserAuthException;
import com.cmj.app.global.encode.PasswordCryptService;
import com.cmj.app.global.encode.RSATextCryptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final PasswordCryptService passwordCryptService;
    private final RSATextCryptService rsaTextCryptService;


    @Transactional
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        if (memberService.existsByUsername(signUpRequest.username())) {
            throw new UserAuthException("이미 사용중인 아이디입니다.");
        }

        if (memberService.existsByEmail(signUpRequest.email())) {
            throw new UserAuthException("이미 사용중인 이메일입니다.");
        }

        Member member = memberService.save(SignUpRequest.toEntity(signUpRequest, passwordCryptService, rsaTextCryptService));
        return SignUpResponse.of(member.getId(), member.getUsername(), member.getEmail(), member.getRole());
    }


}
