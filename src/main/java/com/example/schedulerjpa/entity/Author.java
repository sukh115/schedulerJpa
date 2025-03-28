package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.security.PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "login_id", unique = true)
    private String loginId;

    private String name;
    private String password;

    public Author() {

    }

    public void update(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public Author(String loginId, String name, String encodedPassword) {
        this.loginId = loginId;
        this.name = name;
        this.password = encodedPassword;
    }

    public void isLoginId(String loginId) {
        if (!this.loginId.equals(loginId)) {
            throw new CustomException(ExceptionCode.LOGIN_ID_MISMATCH);
        }
    }

    /**
     * 로그인시 비밀번호 검증
     * @param rawPassword
     * @param passwordEncoder
     */
    public void verifyPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, this.password)) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }
    }



}
