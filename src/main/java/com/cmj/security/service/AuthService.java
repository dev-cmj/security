package com.cmj.security.service;

import com.cmj.security.config.security.jwt.JwtTokenProvider;
import com.cmj.security.domain.entity.Member;
import com.cmj.security.domain.entity.Role;
import com.cmj.security.domain.entity.Roles;
import com.cmj.security.domain.repository.MemberRepository;
import com.cmj.security.dto.MemberRequest;
import com.cmj.security.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse register(MemberRequest memberRequest) {

        if (memberRepository.existsByUsername(memberRequest.username())) {
            throw new RuntimeException("user.already.registered");
        }

        Member member = memberRepository.save(
                Member.builder()
                        .username(memberRequest.username())
                        .password(passwordEncoder.encode(memberRequest.password()))
                        .name(memberRequest.name())
                        .email(memberRequest.email())
                        .phone(memberRequest.phone())
                        .address(memberRequest.address())
                        .role(Role.builder().roleName(Roles.ROLE_USER.name()).build())
                        .build()
        );

        return MemberResponse.of(member);
    }

    public String login(String username, String password) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            throw new RuntimeException("user.already.logged.in");
        }

        try {
            // 사용자 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.generateToken(username);
        } catch (AuthenticationException e) {
            throw new RuntimeException("invalid.username.or.password");
        }
    }

    public void changePassword(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            Member member = memberRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("user.not.found"));

            member.changePassword(passwordEncoder.encode(password));

        } catch (AuthenticationException e) {
            throw new RuntimeException("invalid.username.or.password");
        }
    }

}
