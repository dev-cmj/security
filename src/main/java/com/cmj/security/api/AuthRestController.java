package com.cmj.security.api;

import com.cmj.security.domain.entity.Member;
import com.cmj.security.dto.MemberRequest;
import com.cmj.security.dto.MemberResponse;
import com.cmj.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
