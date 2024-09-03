package com.cmj.security.config;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CachedUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Cacheable(value = "members", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = findMemberByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .authorities(member.getAuthorities())
                .build();
    }

    private Member findMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElse(null);
    }
}