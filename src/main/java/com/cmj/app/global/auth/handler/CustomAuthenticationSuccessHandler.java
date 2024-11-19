package com.cmj.app.global.auth.handler;

import com.cmj.app.global.auth.dto.UserPrincipal;
import com.cmj.app.global.auth.jwt.JwtCookieManager;
import com.cmj.app.global.auth.jwt.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final JwtCookieManager jwtCookieManager;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        response.setStatus(OK.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        String token = jwtProvider.createToken(user.getUsername());
        jwtCookieManager.addTokenToCookie(response, token);

        objectMapper.writeValue(response.getWriter(), "Login successful");

    }
}