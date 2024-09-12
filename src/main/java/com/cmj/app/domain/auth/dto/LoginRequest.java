package com.cmj.app.domain.auth.dto;


import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}
