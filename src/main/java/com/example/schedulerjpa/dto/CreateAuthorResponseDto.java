package com.example.schedulerjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateAuthorResponseDto {
    private final Long authorId;
    private final String loginId;
    private final String name;
    private final String password;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
}
