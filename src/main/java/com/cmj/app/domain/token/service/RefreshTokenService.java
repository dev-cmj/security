package com.cmj.app.domain.token.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.token.entity.RefreshToken;
import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.domain.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public RefreshToken save(Member member, String token, Long expirationTimeInMillis, boolean isRefreshTokenMissing) {

        Optional<RefreshToken> existingToken = refreshTokenRepository.findByMember(member);

        if (existingToken.isPresent()) {
            RefreshToken checkToken = existingToken.get();
            String checkTokenString = checkToken.getToken();

            if (jwtTokenProvider.isTokenExpired(checkTokenString) || jwtTokenProvider.isTokenCloseToExpiration(checkTokenString) || isRefreshTokenMissing) {
                log.info("RefreshToken is expired. Token: {}", checkTokenString);
                return checkToken.updateToken(token);
            } else {
                log.info("RefreshToken is not expired. Token: {}", checkTokenString);
                return checkToken;
            }
        } else {
            Instant expiration = Instant.now().plusMillis(expirationTimeInMillis);
            RefreshToken refreshToken = RefreshToken.builder().token(token).expiration(expiration).member(member).build();
            log.info("RefreshToken is created. Token: {}", token);
            return refreshTokenRepository.save(refreshToken);
        }
    }


}
