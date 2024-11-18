package com.cmj.app.domain.auth.provider;

import jakarta.annotation.PostConstruct;
import kr.goodmit.framework.common.dto.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@ConditionalOnProperty(value = "auth.provider.ad", havingValue = "true", matchIfMissing = true)
public class ADAuthenticationProvider extends ChainableAuthenticationProvider {

    @PostConstruct
    public void init() {
        setPriority(2); // 두 번째로 실행되도록 설정
    }

    @Override
    protected Authentication doAuthenticate(Authentication authentication) throws AuthenticationException {
        log.info("AD 인증 시작");

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 예시: AD 서버에 실제로 인증 로직을 추가
        boolean isAuthenticated = authenticateWithAD(username, password);

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .username(username)
                .password(password)
                .build();

        if (isAuthenticated) {
            log.info("AD 인증 성공: {}", username);
            return new UsernamePasswordAuthenticationToken(userPrincipal, null, getAuthorities(username));
        }
        return null; // 인증 실패 시 null 반환하여 다음 Provider로 넘어감
    }

    private boolean authenticateWithAD(String username, String password) {
        // AD 서버에 실제로 인증 로직을 추가
        return true;
    }

    private List<GrantedAuthority> getAuthorities(String username) {
        // 사용자의 권한을 조회하여 반환
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
