package com.cmj.app.global.util;

import java.security.SecureRandom;

public class DeviceIdUtil {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    // 고유한 디바이스 ID 생성
    public static String generateDeviceId() {
        StringBuilder deviceId = new StringBuilder(12);

        // 4자리 알파벳 대문자 추가
        for (int i = 0; i < 4; i++) {
            deviceId.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        // 4자리 숫자 추가
        for (int i = 0; i < 4; i++) {
            deviceId.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        }

        // 4자리 알파벳 대문자 추가
        for (int i = 0; i < 4; i++) {
            deviceId.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return deviceId.toString();
    }

    // 디바이스 ID가 유효한지 검증
    public static boolean isValidDeviceId(String deviceId) {
        // 패턴은 알파벳 4글자 + 숫자 4글자 + 알파벳 4글자로 구성
        String pattern = "^[A-Z]{4}[0-9]{4}[A-Z]{4}$";
        return deviceId != null && deviceId.matches(pattern);
    }
}