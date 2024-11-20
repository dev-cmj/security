package com.cmj.app.global.encode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class AESCryptServiceImplTest {

    private AESTextCryptService aesCryptService;
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String SEED_KEY = "OjK6mHE1ajPt5XUuoTvOvw==";
    private static final String IV_PARAMETER = "8aa1a1a0715811e9";


    @BeforeEach
    void setUp() {
        aesCryptService = new AESTextCryptService(TRANSFORMATION, ALGORITHM, SEED_KEY, IV_PARAMETER); // 필요 시 Mock 설정 가능
    }

    @Test
    void testEncrypt() {
        // Given
        String plainText = "test";
        String encryptedText = aesCryptService.encrypt(plainText);

        // When & Then
        Assertions.assertNotNull(encryptedText);
        Assertions.assertNotEquals(plainText, encryptedText); // 암호화된 값은 평문과 달라야 함
    }

    @Test
    void testDecrypt() {
        // Given
        String plainText = "test";
        String encryptedText = aesCryptService.encrypt(plainText);

        // When
        String decryptedText = aesCryptService.decrypt(encryptedText);

        // Then
        Assertions.assertEquals(plainText, decryptedText);
    }

    @Test
    void testMultiThreadedEncryptDecrypt() throws InterruptedException, ExecutionException {
        // Given
        String plainText = "test";
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);
        List<Future<Boolean>> results = new ArrayList<>();

        // When
        for (int i = 0; i < threadCount; i++) {
            results.add(executorService.submit(() -> {
                try {
                    String encryptedText = aesCryptService.encrypt(plainText);
                    String decryptedText = aesCryptService.decrypt(encryptedText);
                    return plainText.equals(decryptedText);
                } finally {
                    latch.countDown();
                }
            }));
        }

        latch.await(); // 모든 작업이 완료될 때까지 대기
        executorService.shutdown();

        // Then
        for (Future<Boolean> result : results) {
            Assertions.assertTrue(result.get()); // 모든 결과가 true여야 함
        }
    }

    @Test
    void testBoundaryValues() {
        // Null 입력
        Assertions.assertEquals("", aesCryptService.encrypt(null));

        // 빈 문자열
        String encryptedEmpty = aesCryptService.encrypt("");
        Assertions.assertNotNull(encryptedEmpty);
        Assertions.assertEquals("", encryptedEmpty);
        Assertions.assertEquals("", aesCryptService.decrypt(encryptedEmpty));

        // 매우 긴 문자열
        String longText = "a".repeat(10000); // 10,000자
        String encryptedLongText = aesCryptService.encrypt(longText);
        Assertions.assertNotNull(encryptedLongText);
        Assertions.assertEquals(longText, aesCryptService.decrypt(encryptedLongText));
    }

    @Test
    void testPerformance() {
        // Given
        String plainText = "test";
        int iterations = 10000;
        long startTime = System.currentTimeMillis();

        // When
        for (int i = 0; i < iterations; i++) {
            String encryptedText = aesCryptService.encrypt(plainText);
            String decryptedText = aesCryptService.decrypt(encryptedText);
            Assertions.assertEquals(plainText, decryptedText);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Then
        System.out.println("Performance test completed in " + duration + " ms for " + iterations + " iterations.");
        Assertions.assertTrue(duration < 5000, "Performance test took too long!"); // 5초 내로 완료
    }
}