package com.cmj.app.global.util;

import java.util.Base64;

public class Base64Util {

    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static byte[] decode(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    public static String encodeWithLineBreaks(byte[] data) {
        return Base64.getMimeEncoder().encodeToString(data);
    }

    public static byte[] decodeWithLineBreaks(String base64String) {
        return Base64.getMimeDecoder().decode(base64String);
    }
}