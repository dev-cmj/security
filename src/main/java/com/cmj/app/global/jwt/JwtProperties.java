package com.cmj.app.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class JwtProperties {

    private Cookie cookie;
    private String secretKey;
    private String expiration;

    // 프로퍼티에 값이 설정되지 않았을 경우 기본 값 설정
    public JwtProperties() {
        int time = 3600 * 24;
        this.cookie = Cookie.defaultCookie();
        this.expiration = String.valueOf(time);
    }

    @Getter
    @Setter
    public static class Cookie {
        private String name;
        private String domain;
        private Integer maxAge;
        private Boolean httpOnly;
        private Boolean secure;
        private String path;

        private static Cookie defaultCookie() {
            Cookie cookie = new Cookie();
            cookie.name = "__sid";
            cookie.domain = "";
            cookie.maxAge = 3600;
            cookie.httpOnly = true;
            cookie.secure = false;
            cookie.path = "/";
            return cookie;
        }
    }

    public boolean equalsName(String cookieName) {
        return this.cookie.getName().equals(cookieName);
    }
}
