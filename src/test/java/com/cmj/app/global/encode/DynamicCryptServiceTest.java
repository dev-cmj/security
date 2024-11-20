package com.cmj.app.global.encode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class DynamicCryptServiceTest {

    @Mock
    private AESTextCryptService aesCryptService;

    @Mock
    private RSATextCryptService rsaCryptService;

    private DynamicCryptService dynamicCryptService;

    @BeforeEach
    void setUp() {
        // Mock된 전략들을 서비스에 주입
        List<TextCryptService> services = List.of(aesCryptService, rsaCryptService);

        // 각 전략의 타입을 Mock 설정
        Mockito.when(aesCryptService.getType()).thenReturn("AES");
        Mockito.when(rsaCryptService.getType()).thenReturn("RSA");

        dynamicCryptService = new DynamicCryptService(services);
    }

    @Test
    void testEncryptWithAES() {
        // Given
        String plainText = "test";
        String encryptedText = "encryptedWithAES";

        // AES 암호화에 대한 Mock 동작 설정
        Mockito.when(aesCryptService.encrypt(plainText)).thenReturn(encryptedText);

        // When
        String result = dynamicCryptService.encrypt(plainText, "AES");

        // Then
        Assertions.assertEquals(encryptedText, result);
        Mockito.verify(aesCryptService).encrypt(plainText);
    }

    @Test
    void testEncryptWithRSA() {
        // Given
        String plainText = "test";
        String encryptedText = "encryptedWithRSA";

        // RSA 암호화에 대한 Mock 동작 설정
        Mockito.when(rsaCryptService.encrypt(plainText)).thenReturn(encryptedText);

        // When
        String result = dynamicCryptService.encrypt(plainText, "RSA");

        // Then
        Assertions.assertEquals(encryptedText, result);
        Mockito.verify(rsaCryptService).encrypt(plainText);
    }

    @Test
    void testUnsupportedEncryptionType() {
        // Given
        String plainText = "test";
        String unsupportedType = "UNKNOWN";

        // When & Then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () ->
                dynamicCryptService.encrypt(plainText, unsupportedType)
        );

        Assertions.assertEquals("암호화 타입이 올바르지 않습니다. type=" + unsupportedType, exception.getMessage());
    }
}