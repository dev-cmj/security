package com.cmj.security.config.security;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.repository.MemberRepository;
import com.cmj.security.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Slf4j
public class CachedUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("can't find user. username: " + username));

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(member.getAuthorities())
                .build();
    }


}