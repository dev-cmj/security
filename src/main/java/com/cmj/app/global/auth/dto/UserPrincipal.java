package com.cmj.app.global.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;

    private String name;
    private String email;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static UserPrincipal of(String username, String password, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        return UserPrincipal.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .authorities(authorities)
                .build();
    }

}
