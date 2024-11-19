package com.cmj.app.global.auth.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
        Long memberId,
        String username,
        String email,
        String accessToken,
        String refreshToken,
        Long accessTokenExpiresIn,
        Long refreshTokenExpiresIn
) {

    public static LoginResponse of(Long memberId, String username, String email, String accessToken, String refreshToken, Long accessTokenExpiresIn, Long refreshTokenExpiresIn) {
        return LoginResponse.builder()
                .memberId(memberId)
                .username(username)
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }
}
