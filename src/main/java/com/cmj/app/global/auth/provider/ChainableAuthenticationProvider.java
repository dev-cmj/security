package com.cmj.app.global.auth.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public abstract class ChainableAuthenticationProvider implements AuthenticationProvider {

    private ChainableAuthenticationProvider nextProvider;

    @Setter
    @Getter
    private int priority = Integer.MAX_VALUE;

    public void linkWith(ChainableAuthenticationProvider nextProvider) {
        this.nextProvider = nextProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = doAuthenticate(authentication);

        if (result != null && result.isAuthenticated()) {
            return result;
        }

        if (nextProvider != null) {
            return nextProvider.authenticate(authentication);
        }
        return null;
    }

    // 실제 인증 로직을 각 Provider에서 구현하도록 추상 메서드 추가
    protected abstract Authentication doAuthenticate(Authentication authentication) throws AuthenticationException;

    @Override
    public abstract boolean supports(Class<?> authentication);
}