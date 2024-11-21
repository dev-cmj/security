package com.cmj.app.global.encode;

import com.cmj.app.global.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class AESTextCryptService implements TextCryptService {

    private final String transformation;
    private final String algorithm;
    private final String seedKey;
    private final String ivParameter;

    public AESTextCryptService(@Value("${crypt.aes.transformation}") String transformation,
                                @Value("${crypt.aes.algorithm}") String algorithm,
                                @Value("${crypt.aes.seedkey}") String seedKey,
                                @Value("${crypt.aes.ivparameter}") String ivParameter) {
        this.transformation = Objects.requireNonNull(transformation, "Transformation cannot be null");
        this.algorithm = Objects.requireNonNull(algorithm, "Algorithm cannot be null");
        this.seedKey = Objects.requireNonNull(seedKey, "SeedKey cannot be null");
        this.ivParameter = Objects.requireNonNull(ivParameter, "IV Parameter cannot be null");
    }

    @Override
    public String encrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        try {
            return processCipher(value, Cipher.ENCRYPT_MODE, seedKey, ivParameter);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        try {
            return processCipher(value, Cipher.DECRYPT_MODE, seedKey, ivParameter);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String getType() {
        return "AES";
    }

    private String processCipher(String text, int mode, String seedKey, String iv) throws Exception {
        byte[] keyBytes = Base64Util.decode(seedKey);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, algorithm);

        Cipher cipher = Cipher.getInstance(transformation);
        IvParameterSpec ivSpec = createIv(iv);

        cipher.init(mode, keySpec, ivSpec);

        byte[] inputBytes = (mode == Cipher.ENCRYPT_MODE) ? text.getBytes(StandardCharsets.UTF_8) : Base64Util.decode(text);
        byte[] processedBytes = cipher.doFinal(inputBytes);

        return mode == Cipher.ENCRYPT_MODE
                ? Base64Util.encode(processedBytes)
                : new String(processedBytes, StandardCharsets.UTF_8);
    }

    private IvParameterSpec createIv(String iv) {
        return new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }
}

