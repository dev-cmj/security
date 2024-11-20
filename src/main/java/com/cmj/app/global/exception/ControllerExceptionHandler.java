package com.cmj.app.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ErrorResponse handleException(RuntimeException exception) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = exception.getMessage();
        return new ErrorResponse(internalServerError.value(), message);
    }
}

