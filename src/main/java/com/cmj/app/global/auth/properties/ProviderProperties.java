package com.cmj.app.global.auth.properties;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Getter
@Setter
@ConfigurationProperties(value = "auth.provider")
public class ProviderProperties {
    private boolean db;
    private boolean ad;

    @PostConstruct
    public void init() {
        boolean atLeastOneTrue = false;

        // 리플렉션을 사용하여 모든 boolean 필드를 조회
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getType() == boolean.class) {
                field.setAccessible(true);
                try {
                    if (field.getBoolean(this)) {
                        atLeastOneTrue = true;
                        break;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to access field: " + field.getName(), e);
                }
            }
        }

        // 모든 boolean 필드가 false인 경우 예외 발생
        if (!atLeastOneTrue) {
            throw new IllegalStateException("At least one auth provider must be enabled.");
        }
    }
}