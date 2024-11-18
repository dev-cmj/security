package com.cmj.app.domain.auth.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\": \"Logout successful\"}");
        response.getWriter().flush();
    }
}