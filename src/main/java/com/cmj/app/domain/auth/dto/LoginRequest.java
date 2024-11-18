package com.cmj.app.domain.auth.dto;


import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {

    public void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username is blank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is blank");
        }
    }

    public static LoginRequest of(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
