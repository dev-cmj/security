package com.cmj.app.domain.member.service;

import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.entity.MemberRole;
import com.cmj.app.domain.member.repository.MemberRepository;
import com.cmj.app.global.auth.dto.UserPrincipal;
import com.cmj.app.global.encode.PasswordCryptService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordCryptService passwordCryptService;

    @PostConstruct
    public void init() {
        Member admin = Member.builder()
                .username("admin")
                .password(passwordCryptService.encrypt("admin"))
                .name("admin")
                .email("test")
                .role(MemberRole.ADMIN).build();
        memberRepository.findByUsername("admin").orElseGet(() -> memberRepository.save(admin));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member foundMember = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디가 존재하지 않습니다."));

        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(foundMember.getRole().name()));

        return UserPrincipal.of(
                foundMember.getUsername(),
                foundMember.getPassword(),
                foundMember.getName(),
                foundMember.getEmail(),
                authorities);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void update(Member member) {
        member.update(member, passwordCryptService);
    }

    @Transactional
    public void delete(Member member) {
        memberRepository.delete(member);
    }

}
