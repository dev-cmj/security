package com.cmj.security.config;

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

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/html; charset=utf-8");

        String contextPath = request.getContextPath();
        String loginPath = contextPath + "/api/auth/hello";

        response.getWriter().write("<script>alert('로그인이 필요합니다.'); location.href='" + loginPath + "';</script>");
    }
}