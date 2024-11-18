package com.cmj.app.domain.auth.controller;

import com.cmj.app.domain.auth.dto.LoginRequest;
import com.cmj.app.domain.auth.dto.LoginResponse;
import com.cmj.app.domain.auth.dto.SignUpRequest;
import com.cmj.app.domain.auth.dto.SignUpResponse;
import com.cmj.app.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
