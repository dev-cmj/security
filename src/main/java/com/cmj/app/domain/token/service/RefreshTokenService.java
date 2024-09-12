package com.cmj.app.domain.token.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.token.entity.RefreshToken;
import com.cmj.app.domain.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken save(Member member, String token, Long expirationTimeInMillis) {
        Instant expiration = Instant.now().plusMillis(expirationTimeInMillis);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .expiration(expiration)
                .member(member)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }


}
