package com.cmj.app.global.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class JwtCookieManager {
    private final JwtProperties jwtProperties;

    public void addTokenToCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = ResponseCookie.from(jwtProperties.getCookie().getName(), token)
                .domain(jwtProperties.getCookie().getDomain())
                .httpOnly(jwtProperties.getCookie().getHttpOnly())
                .secure(jwtProperties.getCookie().getSecure())
                .path(jwtProperties.getCookie().getPath())
                .maxAge(Duration.ofSeconds(jwtProperties.getCookie().getMaxAge()))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public void deleteTokenFromCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(jwtProperties.getCookie().getName(), "")
                .domain(jwtProperties.getCookie().getDomain())
                .httpOnly(jwtProperties.getCookie().getHttpOnly())
                .secure(jwtProperties.getCookie().getSecure())
                .path(jwtProperties.getCookie().getPath())
                .maxAge(Duration.ofSeconds(0))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    // 만료 임박 시 쿠키의 age만 갱신
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String token = getTokenFromCookie(request);
        if (token != null) { // 토큰의 만료 임박 여부 체크
            addTokenToCookie(response, token); // 기존 토큰으로 쿠키의 max-age만 갱신
        }
    }


    public String getTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return null;
        for (Cookie cookie : cookies) {
            if (jwtProperties.equalsName(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
