package com.cmj.app.domain.token.repository;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.token.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByMemberAndDeviceId(Member member, String deviceId);

    @Query("SELECT rt FROM RefreshToken rt WHERE rt.member.username = :username AND rt.deviceId = :deviceId")
    Optional<RefreshToken> findByUsernameAndDeviceId(@Param("username") String username, @Param("deviceId") String deviceId);

    Optional<RefreshToken> findByTokenAndDeviceId(String token, String deviceId);

    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.member.username = :username AND rt.deviceId = :deviceId")
    void deleteByUsernameAndDeviceId(@Param("username") String username, @Param("deviceId") String deviceId);

    void deleteByToken(String token);
}
