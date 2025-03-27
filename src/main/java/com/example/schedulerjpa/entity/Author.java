package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.dto.request.CreateAuthorRequestDto;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
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

    public Author(CreateAuthorRequestDto dto) {
        this.loginId = dto.getLoginId();
        this.name = dto.getName();
        this.password = dto.getPassword();
    }

    public void update(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public void isPassword(String password) {
        if (!this.password.equals(password)) {
            throw new CustomException(ExceptionCode.PASSWORD_MISMATCH);
        }
    }

    public void isLoginId(String loginId) {
        if (!this.loginId.equals(loginId)) {
            throw new CustomException(ExceptionCode.LOGINID_MISMATCH);
        }
    }
}
