package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long authorId;
    private String name;
    private String loginId;

}
