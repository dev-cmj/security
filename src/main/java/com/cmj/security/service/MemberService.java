package com.cmj.security.service;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.repository.MemberRepository;
import com.cmj.security.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Cacheable(cacheNames = "members", key = "#username", unless = "#result == null")
    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Transactional
    public Member save(MemberRequest memberRequest) {
        return memberRepository.save(
                Member.builder()
                        .username(memberRequest.username())
                        .password(memberRequest.password())
                        .name(memberRequest.name())
                        .email(memberRequest.email())
                        .phone(memberRequest.phone())
                        .address(memberRequest.address())
                        .build()
        );
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

}
