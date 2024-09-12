package com.cmj.app.domain.auth.controller;

import com.cmj.app.domain.auth.dto.LoginRequest;
import com.cmj.app.domain.auth.dto.LoginResponse;
import com.cmj.app.domain.auth.dto.SignUpRequest;
import com.cmj.app.domain.auth.service.AuthService;
import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        if (authService.isAlreadyLoggedIn(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 로그인된 사용자입니다.");
        }

        boolean isRefreshTokenMissing = authService.isRefreshTokenMissing(request);
        LoginResponse loginResponse = authService.login(loginRequest, isRefreshTokenMissing);

        // 쿠키에 액세스 토큰과 리프레시 토큰 저장
        authService.setTokensInCookies(response, loginResponse);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        // 로그아웃 로직
        authService.logout(token);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        // 회원가입 로직
        authService.signup(signUpRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@CookieValue("RefreshToken") String refreshToken) {
        // 리프레시 토큰을 통한 액세스 토큰 재발급
        authService.refreshToken(refreshToken);

        return ResponseEntity.ok().build();
    }

}
