package com.cmj.security.service;

import com.cmj.security.config.JwtTokenProvider;
import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.entity.Role;
import com.cmj.security.domain.repository.MemberRepository;
import com.cmj.security.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(MemberRequest memberRequest) {

        return memberRepository.save(
                Member.builder()
                        .username(memberRequest.username())
                        .password(passwordEncoder.encode(memberRequest.password()))
                        .name(memberRequest.name())
                        .email(memberRequest.email())
                        .phone(memberRequest.phone())
                        .address(memberRequest.address())
                        .role(Role.builder()
                                .roleName("ROLE_USER")
                                .build()
                        )
                        .build()
        );
    }

    public String login(String username, String password) {
        try {
            // 사용자 인증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                return jwtTokenProvider.generateToken(username);
            } else {
                throw new RuntimeException("Invalid Login Details");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Login Details");
        }
    }

}
