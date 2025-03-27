package com.example.schedulerjpa.dto;

import lombok.Getter;

@Getter
public class CreateScheduleResponseDto {
    private final Long scheduleId;
    private final String authorName;
    private final String title;
    private final String contents;

    public CreateScheduleResponseDto(Long scheduleId, String authorName, String title, String contents) {
        this.scheduleId = scheduleId;
        this.authorName = authorName;
        this.title = title;
        this.contents = contents;
    }
}
