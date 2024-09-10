package com.cmj.security.global.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Response<T> {

    @Builder.Default
    private String code = String.valueOf(HttpStatus.OK.value());

    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();

    private T data;
}