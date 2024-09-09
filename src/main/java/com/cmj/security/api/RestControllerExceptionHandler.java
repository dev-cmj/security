package com.cmj.security.api;

import com.cmj.security.dto.ErrorResponse;
import com.cmj.security.message.MessageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionHandler {

    private final MessageProvider messageProvider;

    @ExceptionHandler(RuntimeException.class)
    public ErrorResponse handleRuntimeException(RuntimeException e) {

        String errorMessage = messageProvider.getMessage(e.getMessage());

        return ErrorResponse.builder()
                .message(errorMessage)
                .code(INTERNAL_SERVER_ERROR.value())
                .status(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();
    }
}
