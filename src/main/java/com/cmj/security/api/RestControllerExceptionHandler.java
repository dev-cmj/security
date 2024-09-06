package com.cmj.security.api;

import com.cmj.security.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException e) {
        return ErrorResponse.builder()
                .message(e.getMessage())
                .code(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }
}
