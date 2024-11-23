package com.cmj.app.global.encode;

public interface PasswordCryptService {
    String encrypt(String password);

    Boolean matches(String rawPassword, String encodedPassword);

    default Boolean isValidPassword(String password) {
        return password != null && password.length() >= 8 && password.matches(".*[a-z].*") && password.matches(".*[A-Z].*") && password.matches(".*[0-9].*") && password.matches(".*[!@#$%^&*()].*");
    }
}
