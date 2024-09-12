package com.cmj.app.domain.member.service;

import com.cmj.app.domain.member.entity.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    Member findById(Long id);
    Member findByUsername(String username);
    boolean existsByUsername(String username);
    void save(Member member);
    void update(Member member);
    void delete(Member member);
}
