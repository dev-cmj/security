package com.cmj.security.api;

import com.cmj.security.config.JwtTokenProvider;
import com.cmj.security.domain.entity.Member;
import com.cmj.security.dto.MemberRequest;
import com.cmj.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    public Member register(@RequestBody MemberRequest memberRequest) {
        log.info("memberRequest: {}", memberRequest);
        return authService.register(memberRequest);
    }


    @PostMapping("/login")
    public String login(@RequestBody MemberRequest memberRequest) {
        return authService.login(memberRequest.username(), memberRequest.password());
    }

}
