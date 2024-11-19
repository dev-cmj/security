package com.cmj.app.global.auth.manager;

import com.cmj.app.global.auth.provider.ChainableAuthenticationProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {

    private final List<ChainableAuthenticationProvider> authChain;

    public CustomAuthenticationManager(List<ChainableAuthenticationProvider> authChain) {
        this.authChain = authChain.stream()
                .sorted(Comparator.comparingInt(ChainableAuthenticationProvider::getPriority))
                .collect(Collectors.toList());

        for (int i = 0; i < this.authChain.size() - 1; i++) {
            this.authChain.get(i).linkWith(this.authChain.get(i + 1));
        }
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        Authentication mainAuthResult = null;
        for (ChainableAuthenticationProvider provider : authChain) {
            mainAuthResult = provider.authenticate(authentication);
            if (mainAuthResult == null || !mainAuthResult.isAuthenticated()) {
                throw new AuthenticationException("Main authentication failed in provider: " + provider.getClass().getSimpleName()) {
                };
            }
        }

        return mainAuthResult; // 모든 인증 성공 시 최종 인증 반환
    }
}