package com.cmj.app.domain.token.repository;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByMemberAndDeviceId(Member member, String deviceId);


    void deleteByTokenAndDeviceId(String token, String deviceId);

    void deleteByToken(String token);
}
