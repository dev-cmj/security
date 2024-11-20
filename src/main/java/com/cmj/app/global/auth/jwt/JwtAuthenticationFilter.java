package com.cmj.app.global.auth.jwt;

import com.cmj.app.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtCookieManager jwtCookieManager;
    private final MemberService memberService;

    private static final List<String> EXCLUDE_PATHS = List.of("/css/", "/js/", "/images/", "/favicon.ico", "/error", "/api/auth/login", "/api/auth/logout");


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return EXCLUDE_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtCookieManager.getTokenFromCookie(request);
            if (token != null && jwtProvider.validateToken(token)) { // 토큰 유효성 검사

//                if (blacklistService.isTokenBlacklisted(token)) { // 블랙리스트 확인
//                    log.info("블랙리스트에 등록된 토큰입니다.");
//                } else {
                    authenticateUserFromToken(token, request); // 인증 처리
//                }
            } else {
                log.info("JWT 만료 또는 유효하지 않은 토큰");
            }
        } catch (Exception e) {
            log.error("JWT 인증 오류", e);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUserFromToken(String token, HttpServletRequest request) {
        String username = jwtProvider.getClaims(token).getSubject();
        UserDetails userDetails = memberService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
