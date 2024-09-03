package com.cmj.security.api;

import com.cmj.security.config.JwtTokenProvider;
import com.cmj.security.domain.entity.Member;
import com.cmj.security.dto.MemberRequest;
import com.cmj.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    public Member register(MemberRequest memberRequest) {
        return authService.register(memberRequest);
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.login(username, password);
    }

}
