package com.cmj.security.unit.service;

import com.cmj.security.global.config.security.jwt.JwtTokenProvider;
import com.cmj.security.domain.member.entity.Member;
import com.cmj.security.domain.member.repository.MemberRepository;
import com.cmj.security.domain.member.dto.MemberRequest;
import com.cmj.security.domain.member.dto.MemberResponse;
import com.cmj.security.domain.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Given
        MemberRequest memberRequest = new MemberRequest("testUser", "password", "John Doe", "test@example.com", "123-456-7890", "123 Main St");
        Member savedMember = Member.builder()
                .username("testUser")
                .password("encodedPassword")
                .name("John Doe")
                .email("test@example.com")
                .phone("123-456-7890")
                .address("123 Main St")
                .role(Role.builder().roleName("ROLE_USER").build())
                .build();

        // Mocking
        when(passwordEncoder.encode(memberRequest.password())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // When
        MemberResponse result = authService.register(memberRequest);

        // Then
        assertNotNull(result);
        assertEquals("testUser", result.username());
        assertEquals("ROLE_USER", result.roles());

        verify(passwordEncoder).encode(memberRequest.password());
        verify(memberRepository).save(any(Member.class));
    }

    @Test
    void testLoginSuccess() {
        // Given
        String username = "testUser";
        String password = "password";
        String token = "generatedToken";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mock(Authentication.class));
        when(jwtTokenProvider.generateToken(username)).thenReturn(token);

        // When
        String result = authService.login(username, password);

        // Then
        assertNotNull(result);
        assertEquals(token, result);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(username);
    }

    @Test
    void testLoginFailure() {
        // Given
        String username = "testUser";
        String password = "wrongPassword";

        doThrow(new AuthenticationException("Invalid username/password") {
        }).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(username, password);
        });

        assertEquals("Invalid username/password supplied", exception.getMessage());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    void testAlreadyLoggedIn() {
        // Given
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("authenticatedUser");

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("testUser", "password");
        });

        assertEquals("Already logged in", exception.getMessage());
    }
}