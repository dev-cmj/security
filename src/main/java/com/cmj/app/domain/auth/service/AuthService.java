package com.cmj.app.domain.auth.service;

import com.cmj.app.domain.auth.dto.LoginRequest;
import com.cmj.app.domain.auth.dto.LoginResponse;
import com.cmj.app.domain.auth.dto.SignUpRequest;
import com.cmj.app.domain.auth.dto.SignUpResponse;
import com.cmj.app.domain.auth.exception.UserAuthException;
import com.cmj.app.domain.member.entity.Member;
import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.domain.token.entity.RefreshToken;
import com.cmj.app.domain.token.exception.ExceptionMessage;
import com.cmj.app.domain.token.provider.JwtTokenProvider;
import com.cmj.app.domain.token.service.RefreshTokenService;
import com.cmj.app.global.util.CookieUtil;
import com.cmj.app.global.util.DeviceIdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, String deviceId) {

        //10분
        Long accessTokenExpiration = 1000L * 60 * 10; // 10분

        Long refreshTokenExpiration = 1000L * 60 * 60 * 24 * 7; // 7일

        Member member = authenticate(loginRequest);
        String accessToken = jwtTokenProvider.generateToken(member.getUsername(), accessTokenExpiration);
        String generatedRefreshToken = jwtTokenProvider.generateToken(member.getUsername(), refreshTokenExpiration);
        RefreshToken refreshToken = refreshTokenService.saveOrUpdate(member, generatedRefreshToken, refreshTokenExpiration, deviceId);

        return LoginResponse.of(member.getId(), member.getUsername(), member.getEmail(), accessToken, refreshToken.getToken(), accessTokenExpiration, refreshTokenExpiration);
    }

    @Transactional
    public void logout(String username, String deviceId) {
        refreshTokenService.deleteByUsernameAndDeviceId(username, deviceId);
    }

    @Transactional
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        if (memberService.existsByUsername(signUpRequest.username())) {
            throw new UserAuthException(ExceptionMessage.EXISTS_USERNAME.getMessage());
        }

        Member member = memberService.save(SignUpRequest.toEntity(signUpRequest, passwordEncoder));
        return SignUpResponse.of(member.getId(), member.getUsername(), member.getEmail(), member.getRole());
    }

    @Transactional
    public void refreshToken(String refreshToken) {

    }

    public boolean isAlreadyLoggedIn(HttpServletRequest request) {
        String accessToken = CookieUtil.getCookie(request, "AccessToken");
        if (accessToken == null) return false;

        String username = jwtTokenProvider.extractUsername(accessToken);
        return username != null && jwtTokenProvider.isTokenValid(accessToken, username);
    }

    public String getOrCreateDeviceId(HttpServletRequest request, HttpServletResponse response) {
        String deviceId = CookieUtil.getCookie(request, "DeviceId");
        if (StringUtils.isEmpty(deviceId) || !DeviceIdUtil.isValidDeviceId(deviceId)) {
            deviceId = DeviceIdUtil.generateDeviceId();
            CookieUtil.createCookie(response, "DeviceId", deviceId, 60 * 60 * 24 * 365L); // 1년 저장
        }
        return deviceId;
    }

    public void setTokensInCookies(HttpServletResponse response, LoginResponse loginResponse) {
        CookieUtil.createCookie(response, "AccessToken", loginResponse.accessToken(), loginResponse.accessTokenExpiresIn());
    }

    public void removeTokensInCookies(HttpServletResponse response) {
        CookieUtil.deleteCookie(response, "AccessToken");
    }

    private Member authenticate(LoginRequest loginRequest) {
        Member member = memberService.findByUsername(loginRequest.username());

        if (!passwordEncoder.matches(loginRequest.password(), member.getPassword())) {
            throw new UserAuthException(ExceptionMessage.MISMATCH_PASSWORD.getMessage());
        }

        return member;
    }

}
