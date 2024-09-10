package com.cmj.security.domain.auth.service;

import com.cmj.security.domain.member.service.MemberService;
import com.cmj.security.global.config.security.jwt.JwtTokenProvider;
import com.cmj.security.domain.member.entity.Member;
import com.cmj.security.domain.member.dto.MemberRequest;
import com.cmj.security.domain.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse register(MemberRequest memberRequest) {

        if (memberService.existsByUsername(memberRequest.username())) {
            throw new RuntimeException("user.already.registered");
        }
        Member member = memberService.save(memberRequest);
        return MemberResponse.of(member);
    }

    public String login(String username, String password) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            throw new RuntimeException("user.already.logged.in");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.generateToken(username);
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw new RuntimeException("invalid.username.or.password");
        }
    }

    public void changePassword(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            Member member = memberService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("user.not.found"));

            member.changePassword(passwordEncoder.encode(password));

        } catch (AuthenticationException e) {
            throw new RuntimeException("invalid.username.or.password");
        }
    }

}
