package com.cmj.app.domain.token.provider;

import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.domain.token.exception.ExceptionMessage;
import com.cmj.app.domain.token.exception.TokenCheckFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager {
    private final MemberService memberService;

//    private final LogoutAccessTokenService logoutAccessTokenService;

    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(String jwt) {
        String tokenUserName = jwtTokenProvider.extractUsername(jwt);

        if (tokenUserName == null) {
            throw new TokenCheckFailException(ExceptionMessage.MISMATCH_USERNAME_TOKEN.getMessage());
        }

        UserDetails userDetails = memberService.loadUserByUsername(tokenUserName);

        if (!userDetails.getUsername().equals(tokenUserName)) {
            throw new TokenCheckFailException(ExceptionMessage.MISMATCH_USERNAME_TOKEN.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            throw new TokenCheckFailException(ExceptionMessage.FAIL_TOKEN_CHECK.getMessage());
        } else if (this.jwtTokenProvider.isTokenExpired(token)) {
            throw new TokenCheckFailException(ExceptionMessage.TOKEN_VALID_TIME_EXPIRED.getMessage());
        } else if (checkLogout(token)) {
            throw new TokenCheckFailException(ExceptionMessage.ALREADY_LOGOUT_USER.getMessage());
        }
        return true;
    }

    private boolean checkLogout(String token) {
//        return logoutAccessTokenService.existsLogoutAccessTokenById(token);
        return true;
    }
}