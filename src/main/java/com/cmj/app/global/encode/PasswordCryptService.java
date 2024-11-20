package com.cmj.app.global.encode;

public interface PasswordCryptService {
    String encrypt(String password);
    Boolean matches(String rawPassword, String encodedPassword);
}
