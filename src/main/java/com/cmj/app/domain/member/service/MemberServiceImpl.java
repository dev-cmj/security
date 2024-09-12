package com.cmj.app.domain.member.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member foundMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        return User.builder()
                .username(foundMember.getUsername())
                .password(foundMember.getPassword())
                .roles(foundMember.getRole().name())
                .build();
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional
    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    @Transactional
    @Override
    public void update(Member member) {
        member.update(member, passwordEncoder);
    }

    @Transactional
    @Override
    public void delete(Member member) {
        memberRepository.delete(member);
    }

}
