package com.example.schedulerjpa.security;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA-256 알고리즘을 사용하여 비밀번호를 암호화하고,
 * 입력값과 암호화된 값을 비교하는 간단한 PasswordEncoder 구현체
 */
@Component
public class SimplePaswordEncoder implements PasswordEncoder{

    /**
     * 비밀번호를 SHA-256 방식으로 해싱하여 암호화합니다.
     *
     * @param rawPassword 사용자가 입력한 비밀번호
     * @return 해싱된 암호 문자열 (16진수)
     */
    @Override
    public String encode(String rawPassword) {
        try {
            // SHA-256 알고리즘을 사용하는 MessageDigest 인스턴스 생성
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // 입력한 비밀번호를 UTF-8 바이트 배열로 변환하여 해싱 수행
            byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));

            // 해싱된 바이트 배열을 16진수 문자열로 변환
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                // 바이트 값을 2자리 16진수로 포맷팅하여 문자열에 추가
                hex.append(String.format("%02x", b));
            }
            //최종적으로 생성된 해시 문자열 반환
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256 알고리즘을 사용할 수 없는 경우 런타임 예외 발생
            throw new RuntimeException("암호화에 실패했습니다", e);
        }
    }

    /**
     * 입력된 비밀번호를 encode()한 결과가 기존 해시된 비밀번호와 일치하는지 확인합니다.
     *
     * @param rawPassword 사용자가 로그인 시 입력한 비밀번호
     * @param encodedPassword DB 등에 저장된 암호화된 비밀번호
     * @return 일치 여부
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
