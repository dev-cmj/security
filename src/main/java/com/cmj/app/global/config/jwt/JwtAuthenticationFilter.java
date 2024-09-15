package com.cmj.app.global.config.jwt;

import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.domain.token.service.RefreshTokenService;
import com.cmj.app.global.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.cmj.app.global.util.DeviceIdUtil.isValidDeviceId;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public JwtAuthenticationFilter(@Qualifier("memberService") UserDetailsService userDetailsService,
                                   JwtTokenProvider jwtTokenProvider,
                                   RefreshTokenService refreshTokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/") || path.startsWith("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Processing request: {}", request.getRequestURI());

        String accessToken = CookieUtil.getCookie(request, "AccessToken");
        String deviceId = CookieUtil.getCookie(request, "DeviceId");

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isNotEmpty(accessToken)) {
            handleAccessToken(accessToken, request, response, deviceId);
        }

        filterChain.doFilter(request, response);
    }

    private void handleAccessToken(String accessToken, HttpServletRequest request, HttpServletResponse response, String deviceId) {
        try {

            boolean isAccessTokenExpired = jwtTokenProvider.isTokenExpired(accessToken);
            String username = jwtTokenProvider.extractUsername(accessToken);

            if (isAccessTokenExpired) {
                log.info("AccessToken expired. Attempting to refresh.");
                handleRefreshToken(deviceId, username, request, response);
            } else if (StringUtils.isNotEmpty(username)) {
                authenticateUser(username, accessToken, request);
            }
        } catch (Exception e) {
            log.error("Error verifying JWT token", e);
            deleteCookies(response);
        }
    }

    private void handleRefreshToken(String deviceId, String username, HttpServletRequest request, HttpServletResponse response) {

        if (StringUtils.isEmpty(deviceId)) {
            log.warn("DeviceId is missing or invalid.");
            deleteCookies(response);
            return;
        }

        if (!isValidDeviceId(deviceId)) {
            log.warn("Invalid DeviceId detected. Potential tampering attempt.");
            deleteCookies(response);
            return;
        }

        refreshTokenService.findByUsernameAndDeviceId(username, deviceId).ifPresentOrElse(
                token -> {
                    //accessToken의 갱신 시간은 10분으로 설정
                    String newAccessToken = jwtTokenProvider.generateToken(username, 10 * 60 * 1000L);

                    CookieUtil.createCookie(response, "AccessToken", newAccessToken, 10 * 60 * 1000L);
                    authenticateUser(username, newAccessToken, request);
                },
                () -> {
                    log.warn("RefreshToken not found in DB.");
                    deleteCookies(response);
                }
        );
    }

    private void authenticateUser(String username, String token, HttpServletRequest request) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        if (jwtTokenProvider.isTokenValid(token, userDetails.getUsername())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    private void deleteCookies(HttpServletResponse response) {
        CookieUtil.deleteCookie(response, "AccessToken");
    }

}
