package com.cmj.app.global.auth.handler;


import com.cmj.app.global.auth.jwt.JwtCookieManager;
import com.cmj.app.global.auth.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final JwtCookieManager jwtCookieManager;
    private final JwtProvider jwtProvider;


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = extractToken(request);

//        if (token != null) {
//            //만료시간 1일
//            blacklistService.addToken(token, 86400000);
//        }

        // 추가 작업 : 쿠키 삭제
        jwtCookieManager.deleteTokenFromCookie(response);

    }

    private String extractToken(HttpServletRequest request) {
        return jwtCookieManager.getTokenFromCookie(request);
    }

}