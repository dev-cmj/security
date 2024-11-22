package com.cmj.app.global.auth.provider;


import com.cmj.app.domain.member.service.MemberService;
import com.cmj.app.global.encode.PasswordCryptService;
import com.cmj.app.global.encode.RSATextCryptService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "auth.provider.db", havingValue = "true", matchIfMissing = true)
@Slf4j
@RequiredArgsConstructor
public class DBAuthenticationProvider extends ChainableAuthenticationProvider {

    private final MemberService memberService;
    private final PasswordCryptService passwordCryptService;
    private final RSATextCryptService rsaTextCryptService;

    @PostConstruct
    public void init() {
        setPriority(1); // 첫 번째로 실행되도록 설정
    }

    @Override
    protected Authentication doAuthenticate(Authentication authentication) throws AuthenticationException {
        log.info("DB 인증 시작");

        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
        String decrypt = rsaTextCryptService.decrypt(rawPassword);

        // DB에서 사용자 조회
        UserDetails userDetails = authenticateWithDB(username);

        if (userDetails != null && passwordCryptService.matches(decrypt, userDetails.getPassword())) {
            log.info("DB 인증 성공: {}", username);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        } else {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.") {
            };
        }
    }

    private UserDetails authenticateWithDB(String username) {
        return memberService.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}