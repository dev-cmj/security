package com.cmj.app.domain.auth.service;

import com.cmj.app.domain.auth.dto.LoginRequest;
import com.cmj.app.domain.auth.dto.LoginResponse;
import com.cmj.app.domain.auth.dto.SignUpRequest;
import com.cmj.app.domain.auth.exception.UserAuthException;
import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.domain.token.entity.RefreshToken;
import com.cmj.app.domain.token.exception.ExceptionMessage;
import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.domain.token.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest) {

        Long accessTokenExpiration = 1000L * 60 * 60; // 1시간
        Long refreshTokenExpiration = 1000L * 60 * 60 * 24 * 7; // 7일

        Member member = authenticate(loginRequest);
        String accessToken = jwtTokenProvider.generateToken(member.getUsername(), accessTokenExpiration);
        String generatedRefreshToken = jwtTokenProvider.generateToken(member.getUsername(), refreshTokenExpiration);
        RefreshToken refreshToken = refreshTokenService.save(member, generatedRefreshToken, refreshTokenExpiration);

        return LoginResponse.of(member.getId(), member.getUsername(), member.getEmail(), accessToken, refreshToken.getToken(), accessTokenExpiration, refreshTokenExpiration);
    }

    @Transactional
    public void logout(String token) {
    }

    @Transactional
    public void signup(SignUpRequest signUpRequest) {
    }

    @Transactional
    public void refreshToken(String refreshToken) {
    }


    private Member authenticate(LoginRequest loginRequest) {
        Member member = memberService.findByUsername(loginRequest.username());

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new UserAuthException(ExceptionMessage.MISMATCH_PASSWORD.getMessage());
        }

        return member;
    }

}
