package com.cmj.app.domain.token.exception;

import lombok.Getter;

@Getter
public class TokenCheckFailException extends RuntimeException {

    public TokenCheckFailException(String message) {
        super(message);
    }

}
