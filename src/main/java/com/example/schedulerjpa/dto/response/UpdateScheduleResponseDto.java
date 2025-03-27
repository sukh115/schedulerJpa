package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateScheduleResponseDto {
    private String title;
    private String contents;
    private String authorName;
    private LocalDateTime updatedDate;
}
