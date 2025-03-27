package com.example.schedulerjpa.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private final String authorName;
    private final String title;
    private final String contents;
    private final LocalDateTime updatedDate;

    public ScheduleResponseDto(String authorName, String title, String contents, LocalDateTime updatedDate) {
        this.authorName = authorName;
        this.title = title;
        this.contents = contents;
        this.updatedDate = updatedDate;
    }
}
