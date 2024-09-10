package com.cmj.security.domain.auth.controller;

import com.cmj.security.domain.member.dto.MemberRequest;
import com.cmj.security.domain.member.dto.MemberResponse;
import com.cmj.security.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/register")
    public MemberResponse register(@RequestBody MemberRequest memberRequest) {
        return authService.register(memberRequest);
    }


    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody MemberRequest memberRequest) {
        String token = authService.login(memberRequest.username(), memberRequest.password());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return ResponseEntity.ok().headers(headers).build();
    }

    @PutMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody MemberRequest memberRequest) {
        authService.changePassword(memberRequest.username(), memberRequest.password());
        return ResponseEntity.ok().build();
    }

}
