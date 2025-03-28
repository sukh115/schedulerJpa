package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.security.PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 작성자 엔티티 클래스
 */
@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity {
    // 작성자 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    // 로그인 ID (중복 불가)
    @Column(name = "login_id", unique = true)
    private String loginId;

    private String name; // 작성자 이름
    private String password; // 비밀번호

    // 기본 생성자
    public Author() {

    }

    /**
     * 생성자 - 신규 작성자 등록 시 사용
     *
     * @param loginId         로그인 ID
     * @param name            작성자 이름
     * @param encodedPassword 암호화된 비밀번호
     */
    public Author(String loginId, String name, String encodedPassword) {
        this.loginId = loginId;
        this.name = name;
        this.password = encodedPassword;
    }

    /**
     * 작성자 정보를 수정합니다.
     *
     * @param loginId 수정할 로그인 ID
     * @param name    수정할 이름
     */
    public void update(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    /**
     * 로그인 ID가 일치하는지 검증합니다.
     *
     * @param loginId 요청한 로그인 ID
     * @throws CustomException 로그인 ID가 일치하지 않으면 예외 발생
     */
    public void isLoginId(String loginId) {
        if (!this.loginId.equals(loginId)) {
            throw new CustomException(ExceptionCode.LOGIN_ID_MISMATCH);
        }
    }

    /**
     * 비밀번호가 일치하는지 검증합니다.
     *
     * @param rawPassword     입력된 원본 비밀번호
     * @param passwordEncoder 비밀번호 암호화 비교기
     * @throws CustomException 비밀번호 불일치 시 예외 발생
     */
    public void verifyPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, this.password)) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }
    }


}
