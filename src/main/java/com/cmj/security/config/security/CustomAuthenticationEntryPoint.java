package com.cmj.security.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

//401 Unauthorized
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // 요청의 Accept 헤더를 가져옴
        String accept = request.getHeader("Content-Type");

        // 요청이 JSON 형식을 원할 경우
        if (accept != null && accept.contains("application/json")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=utf-8");

            // JSON 형식의 응답을 작성
            String jsonResponse = "{\"message\": \"로그인이 필요합니다.\", \"redirectUrl\": \"/api/auth/hello\"}";
            response.getWriter().write(jsonResponse);

        } else { // 요청이 HTML 형식을 원할 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/html; charset=utf-8");

            // HTML 형식의 응답을 작성
            String contextPath = request.getContextPath();
            String loginPath = contextPath + "/api/auth/hello";
            response.getWriter().write("<script>alert('로그인이 필요합니다.'); location.href='" + loginPath + "';</script>");
        }
    }
}