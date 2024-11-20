package com.cmj.app.global.encode;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicCryptService {

    private final Map<String, TextCryptService> cryptServices;

    public DynamicCryptService(List<TextCryptService> services) {
        this.cryptServices = services.stream()
                .collect(Collectors.toUnmodifiableMap(TextCryptService::getType, service -> service));
    }

    public String encrypt(String value, String type) {
        return getCryptService(type).encrypt(value);
    }

    public String decrypt(String value, String type) {
        return getCryptService(type).decrypt(value);
    }

    private TextCryptService getCryptService(String type) {
        return Optional.ofNullable(cryptServices.get(type))
                .orElseThrow(() -> new IllegalArgumentException("암호화 타입이 올바르지 않습니다. type=" + type));
    }
}