package com.cmj.app.domain.auth.controller;

import com.cmj.app.domain.auth.dto.LoginRequest;
import com.cmj.app.domain.auth.dto.LoginResponse;
import com.cmj.app.domain.auth.dto.SignUpRequest;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {

        if (authService.isAlreadyLoggedIn(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 로그인된 사용자입니다.");
        }

        String deviceId = authService.getOrCreateDeviceId(request, response); // 디바이스 ID 생성 또는 조회
        LoginResponse loginResponse = authService.login(loginRequest, deviceId);

        // 쿠키에 액세스 토큰과 리프레시 토큰 저장
        authService.setTokensInCookies(response, loginResponse);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue("DeviceId") String deviceId,
                                    Authentication authentication,
                                    HttpServletResponse response) {
        authService.logout(authentication.getName(), deviceId);
        authService.removeTokensInCookies(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest, HttpServletRequest request) {
        if (authService.isAlreadyLoggedIn(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그아웃 후 회원가입을 시도해주세요.");
        }

        authService.signup(signUpRequest);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(HttpServletRequest request) {
        boolean loggedIn = authService.isAlreadyLoggedIn(request);
        return ResponseEntity.ok(loggedIn);
    }

}
