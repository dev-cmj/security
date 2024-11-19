package com.cmj.app.global.auth.filter;


import com.cmj.app.global.auth.dto.LoginRequest;
import com.cmj.app.global.auth.handler.CustomAuthenticationFailureHandler;
import com.cmj.app.global.auth.handler.CustomAuthenticationSuccessHandler;
import com.cmj.app.global.auth.jwt.JwtCookieManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;

@Component
@Slf4j
public class CustomLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final JwtCookieManager jwtCookieManager;

    public CustomLoginFilter(AuthenticationManager authenticationManager,
                             ObjectMapper objectMapper,
                             JwtCookieManager jwtCookieManager,
                             CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler,
                             CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        // 특정 URL과 HTTP 메서드로 필터 동작 설정
        super(new AntPathRequestMatcher("/api/login", "POST"));
        this.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        this.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        this.objectMapper = objectMapper;
        this.jwtCookieManager = jwtCookieManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!request.getMethod().equals(POST.name())) {
            throw new IllegalStateException("Authentication method not supported: " + request.getMethod());
        }

        try {

            LoginRequest loginRequest = objectMapper.readValue(request.getReader(), LoginRequest.class);
            loginRequest.validate();

            String username = loginRequest.username();
            String password = loginRequest.password();

            log.info("username: {}, password: {}", username, password);

            //이미 쿠키가 있는 경우 로그인 요청을 거부합니다.
            if (jwtCookieManager.getTokenFromCookie(request) != null) {
                throw new IllegalStateException("이미 로그인되어 있습니다.");
            }

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (IOException e) {
            throw new IllegalArgumentException("알 수 없는 요청입니다.");
        }
    }
}
