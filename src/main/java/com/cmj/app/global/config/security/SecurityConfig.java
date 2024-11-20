package com.cmj.app.global.config.security;

import com.cmj.app.global.auth.filter.CustomLoginFilter;
import com.cmj.app.global.auth.filter.CustomLogoutFilter;
import com.cmj.app.global.auth.handler.CustomAccessDeniedHandler;
import com.cmj.app.global.auth.jwt.JwtAuthenticationFilter;
import com.cmj.app.global.auth.manager.CustomAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLoginFilter customLoginFilter;
    private final CustomLogoutFilter customLogoutFilter;
    private final CustomAuthenticationManager authenticationManager;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        (request) ->
                                request.requestMatchers("/api/auth/logout").permitAll()
                                        .requestMatchers("/api/auth/login").anonymous() // 로그인 api 는 익명 유저만 가능
                                        .requestMatchers("/api/auth/public-key").anonymous() // 공개키 api 는 익명 유저만 가능
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/auth/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/css/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/js/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/images/**")).permitAll()
                                        .anyRequest().authenticated())
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // 세션 사용 안함
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authenticationManager(authenticationManager) // 커스텀 인증 매니저 설정

                .addFilterBefore(customLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customLogoutFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

                .formLogin(AbstractHttpConfigurer::disable) // 필요 시 폼 로그인 설정 비활성화
                .logout(AbstractHttpConfigurer::disable) // 필요 시 로그아웃 설정 비활성화

                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler))
        ;

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
