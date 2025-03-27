package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDto {
    private String title;
    private String contents;
    private String authorName;
    private LocalDateTime updatedDate;
}
