package com.cmj.app.global.auth.dto;


import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {

    public void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }

    public static LoginRequest of(String username, String password) {
        return LoginRequest.builder()
                .username(username)
                .password(password)
                .build();
    }
}
