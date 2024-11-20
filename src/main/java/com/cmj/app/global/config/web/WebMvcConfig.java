package com.cmj.app.global.config.web;

import com.cmj.app.global.interceptor.GlobalInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String[] STATIC_RESOURCES = {
            "/js/**",
            "/css/**",
            "/img/**",
            "/file/**",
            "/common/**",
            "/home",
    };

    private final GlobalInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(STATIC_RESOURCES);
    }
}
