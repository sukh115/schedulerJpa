package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuthorResponseDto {
    private final String loginId;
    private final String name;
    private final LocalDateTime updatedDate;
}
