package com.cmj.security.domain.member.repository;

import com.cmj.security.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByUsername(String username);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);
    void deleteByUsername(String username);
}
