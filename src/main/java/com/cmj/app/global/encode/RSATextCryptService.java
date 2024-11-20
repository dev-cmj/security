package com.cmj.app.global.encode;

import com.cmj.app.global.util.Base64Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

@Service
public class RSATextCryptService implements TextCryptService {

    private final String publicKey;
    private final String privateKey;

    public RSATextCryptService(@Value("${crypt.rsa.public-key}") String publicKey,
                               @Value("${crypt.rsa.private-key}") String privateKey) {
        this.publicKey = Objects.requireNonNull(publicKey, "Public key cannot be null");
        this.privateKey = Objects.requireNonNull(privateKey, "Private key cannot be null");
    }

    @Override
    public String encrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PublicKey key = getPublicKey(publicKey);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64Util.encode(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("RSA Encryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String decrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            PrivateKey key = getPrivateKey(privateKey);
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decryptedBytes = cipher.doFinal(Base64Util.decode(value));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("RSA Decryption failed: " + e.getMessage(), e);
        }
    }

    @Override
    public String getType() {
        return "RSA";
    }

    private PublicKey getPublicKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64Util.decode(base64Key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    private PrivateKey getPrivateKey(String base64Key) throws Exception {
        byte[] keyBytes = Base64Util.decode(base64Key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(spec);
    }
}