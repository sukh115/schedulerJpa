package com.example.schedulerjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateAuthorResponseDto {
    private final String loginId;
    private final String name;
    private final LocalDateTime updatedDate;
}
