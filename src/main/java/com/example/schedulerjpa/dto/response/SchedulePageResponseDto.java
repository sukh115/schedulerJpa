package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SchedulePageResponseDto {
    private final String title;
    private final String contents;
    private final String authorName;
    private final Long countComment;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
}
