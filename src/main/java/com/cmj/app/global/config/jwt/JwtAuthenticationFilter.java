package com.cmj.app.global.config.jwt;

import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider JwtTokenProvider;

    public JwtAuthenticationFilter(@Qualifier("memberService") UserDetailsService userDetailsService, JwtTokenProvider JwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.JwtTokenProvider = JwtTokenProvider;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") || path.startsWith("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 나머지 로직...
        // Authorization 헤더 대신 쿠키에서 JWT 추출
        String jwt = CookieUtil.getCookie(request, "AccessToken"); // "JWT-TOKEN" 쿠키 이름으로 JWT 가져오기
        String username = null;

        if (jwt != null) {
            username = JwtTokenProvider.extractUsername(jwt); // JWT로부터 사용자 이름 추출
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (JwtTokenProvider.isTokenValid(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                log.error("JWT 토큰 유효성 검사 실패");
                CookieUtil.deleteCookie(response, "AccessToken");
                CookieUtil.deleteCookie(response, "RefreshToken");
            }
        }

        filterChain.doFilter(request, response);
    }
}