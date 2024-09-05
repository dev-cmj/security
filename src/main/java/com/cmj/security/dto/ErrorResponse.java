package com.cmj.security.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(String message,
                            int code,
                            String status) {
}
