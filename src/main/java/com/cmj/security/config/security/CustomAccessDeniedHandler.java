package com.cmj.security.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // 요청의 Accept 헤더를 가져옴
        String accept = request.getHeader("Content-Type");

        System.out.println("accept = " + accept);

        System.out.println("accessDeniedException = " + accessDeniedException.getMessage());


        // 요청이 JSON 형식을 원할 경우
        if (accept != null && accept.contains("application/json")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json; charset=utf-8");

            // JSON 형식의 응답을 작성
            String jsonResponse = "{\"message\": \"접근 권한이 없습니다.\", \"redirectUrl\": \"/api/auth/hello\"}";
            response.getWriter().write(jsonResponse);

        } else { // 요청이 HTML 형식을 원할 경우
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/html; charset=utf-8");

            // HTML 형식의 응답을 작성
            String contextPath = request.getContextPath();
            String loginPath = contextPath + "/api/auth/hello";
            response.getWriter().write("<script>alert('접근 권한이 없습니다.'); location.href='" + loginPath + "';</script>");
        }
    }
}