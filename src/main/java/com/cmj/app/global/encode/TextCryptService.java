package com.cmj.app.global.encode;

/**
 * 도메인에서 텍스트를 암호화할 때 사용되는 interface. 도메인에서 사용할 해당 interface의 실 객체는 도메인을 사용하는 외부 모듈에서 보안정책에 맞는 암호화 로직을 구현한다.
 */
public interface TextCryptService {
    String encrypt(String value);
    String decrypt(String value);
    String getType();
}
