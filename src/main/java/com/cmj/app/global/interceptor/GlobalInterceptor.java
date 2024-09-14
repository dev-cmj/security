package com.cmj.app.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        // 요청 경로 확인
//        String requestURI = request.getRequestURI();
//
//        // 로그인 페이지나 회원가입 페이지 등은 리다이렉트하지 않음
//        if (requestURI.startsWith("/auth/login") || requestURI.startsWith("/auth/signup")) {
//            return true; // 로그인 또는 회원가입 페이지는 통과
//        }
//
//        // SecurityContext에서 인증 정보 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // anonymous user인 경우 로그인 페이지로 리다이렉트
//        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"))) {
//            response.sendRedirect("/auth/login");
//            return false; // 요청 처리 중단
//        }
//
//        // 인증된 경우 요청 진행
//        return true;
//    }
}