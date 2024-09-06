package com.cmj.security.integration;

import com.cmj.security.dto.MemberRequest;
import com.cmj.security.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
class AuthIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = String.format("http://localhost:%d/api/auth", port);
    }

    @Test
    void testRegister() {
        // Given
        MemberRequest memberRequest = new MemberRequest("testUser", "password", "John Doe", "test@example.com", "123-456-7890", "123 Main St");

        // When
        ResponseEntity<MemberResponse> response = restTemplate.postForEntity(baseUrl + "/register", memberRequest, MemberResponse.class);

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("testUser", response.getBody().username());
        assertEquals("test@example.com", response.getBody().email());
    }

    @Test
    void testLogin() {
        // Given: 회원가입 먼저 진행
        MemberRequest memberRequest = new MemberRequest("testUser2", "password", "John Doe2", "test2@example.com", "123-456-7880", "123 Main St");
        restTemplate.postForEntity(baseUrl + "/register", memberRequest, MemberResponse.class);

        // When: 로그인 요청
        ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl + "/login", memberRequest, Void.class);

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertTrue(response.getHeaders().containsKey("Authorization"));
        assertNotNull(response.getHeaders().get("Authorization"));
    }
}