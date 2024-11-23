package com.cmj.app.global.auth.controller;

import com.cmj.app.global.auth.dto.SignUpRequest;
import com.cmj.app.global.auth.dto.SignUpResponse;
import com.cmj.app.global.auth.dto.UserPrincipal;
import com.cmj.app.global.auth.service.AuthService;
import com.cmj.app.global.encode.RSATextCryptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    private final RSATextCryptService rsaTextCryptService;

    @GetMapping("/status")
    public ResponseEntity<?> status(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(userPrincipal.toResponse());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest signUpRequest, HttpServletRequest request) {
        if (request.getUserPrincipal() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 후 회원가입을 시도해주세요.");
        }

        SignUpResponse signUpResponse = authService.signup(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }

    @GetMapping("/public-key")
    public ResponseEntity<?> getPublicKey() {
        return ResponseEntity.ok(rsaTextCryptService.getPublicKey());
    }

}
