package com.cmj.app.domain.token.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.token.entity.RefreshToken;
import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.domain.token.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public RefreshToken saveOrUpdate(Member member, String newToken, Long expirationTimeInMillis, String deviceId) {

        // 특정 멤버와 디바이스 ID에 해당하는 RefreshToken 조회
        Optional<RefreshToken> existingToken = findByMemberAndDeviceId(member, deviceId);

        if (existingToken.isPresent()) {
            RefreshToken refreshToken = existingToken.get();

            // 토큰이 만료되었거나, 만료시간이 임박한 경우 갱신
            if (jwtTokenProvider.isTokenExpired(refreshToken.getToken()) || jwtTokenProvider.isTokenCloseToExpiration(refreshToken.getToken())) {
                refreshToken.updateToken(newToken, expirationTimeInMillis);
                log.info("RefreshToken is updated for deviceId: {}", deviceId);
            }

            // 만료되지 않은 경우 기존 토큰 반환
            return refreshToken;

        } else {
            // 새로 생성된 토큰 저장
            Instant expiration = Instant.now().plusMillis(expirationTimeInMillis);
            RefreshToken newRefreshToken = RefreshToken.builder()
                    .token(newToken)
                    .expiration(expiration)
                    .member(member)
                    .deviceId(deviceId)  // 각 디바이스별로 관리
                    .build();

            log.info("New RefreshToken is created for deviceId: {}", deviceId);
            return refreshTokenRepository.save(newRefreshToken);
        }
    }

    @Transactional
    public void deleteByTokenAndDeviceId(String token, String deviceId) {
        refreshTokenRepository.deleteByTokenAndDeviceId(token, deviceId);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByMemberAndDeviceId(Member member, String deviceId) {
        return refreshTokenRepository.findByMemberAndDeviceId(member, deviceId);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findByTokenAndDeviceId(String token, String deviceId) {
        return refreshTokenRepository.findByTokenAndDeviceId(token, deviceId);
    }


}
