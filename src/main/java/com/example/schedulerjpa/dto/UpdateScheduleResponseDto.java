package com.example.schedulerjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateScheduleResponseDto {
    private String authorName;
    private String title;
    private String contents;
    private LocalDateTime updatedDate;
}
