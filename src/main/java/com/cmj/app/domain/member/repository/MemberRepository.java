package com.cmj.app.domain.member.repository;

import com.cmj.app.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);
}
