package com.cmj.security.global.common;

import lombok.Builder;

@Builder
public record ErrorResponse(String message,
                            int code,
                            String status) {
}
