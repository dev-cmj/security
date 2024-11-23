package com.cmj.app.global.exception;

import com.cmj.app.global.auth.exception.UserAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(RuntimeException exception) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = exception.getMessage();
        return new ErrorResponse(internalServerError.value(), message);
    }

    @ExceptionHandler(value = UserAuthException.class)
    public ResponseEntity<ErrorResponse> handleUserAuthException(UserAuthException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST; // 400 에러
        String message = exception.getMessage();
        return new ResponseEntity<>(new ErrorResponse(badRequest.value(), message), badRequest);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST; // 400 에러
        String message = Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse(badRequest.value(), message), badRequest);
    }
}

