package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.dto.CreateAuthorRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "author")
public class Author extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "login_id", unique = true)
    private String loginId;

    private String name;
    private String password;

    public Author(CreateAuthorRequestDto dto) {
        this.loginId = dto.getLoginId();
        this.name = dto.getName();
        this.password = dto.getPassword();
    }
}
