package com.cmj.app.domain.token.exception;

public enum ExceptionMessage {
    MISMATCH_USERNAME_TOKEN("토큰의 유저네임과 일치하지 않습니다."),
    FAIL_TOKEN_CHECK("토큰 검증에 실패하였습니다."),
    TOKEN_VALID_TIME_EXPIRED("토큰의 유효시간이 만료되었습니다."),
    ALREADY_LOGOUT_USER("이미 로그아웃된 사용자입니다."),
    NOT_FOUND_USERNAME("해당 유저네임을 찾을 수 없습니다."),
    MISMATCH_PASSWORD("비밀번호가 일치하지 않습니다."),
    EXISTS_USERNAME("이미 존재하는 유저네임입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
