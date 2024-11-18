package com.cmj.app.domain.auth.controller;

import com.cmj.app.domain.auth.dto.SignUpRequest;
import com.cmj.app.domain.auth.dto.SignUpResponse;
import com.cmj.app.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
@Slf4j
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if (authService.isAlreadyLoggedIn(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 후 회원가입을 시도해주세요.");
        }

        SignUpResponse signUpResponse = authService.signup(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }

}
