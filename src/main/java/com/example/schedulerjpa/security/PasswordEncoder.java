package com.example.schedulerjpa.security;

/**
 * 비밀번호 암호화 및 검증을 위한 전략 인터페이스입니다.
 */
public interface PasswordEncoder {

    /**
     * 원문 비밀번호를 암호화합니다.
     *
     * @param rawPassword 암호화 전 원문 비밀번호
     * @return 암호화된 비밀번호 문자열
     */
    String encode(String rawPassword);

    /**
     * 입력된 원문 비밀번호와 저장된 암호화 비밀번호가 일치하는지 검사합니다.
     *
     * @param rawPassword     입력된 원문 비밀번호
     * @param encodedPassword 저장된 암호화 비밀번호
     * @return 비밀번호가 일치하면 true, 아니면 false
     */
    boolean matches(String rawPassword, String encodedPassword);
}
