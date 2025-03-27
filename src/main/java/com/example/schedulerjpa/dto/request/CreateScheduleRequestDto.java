package com.example.schedulerjpa.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateScheduleRequestDto {
    private String title;
    private String contents;
}
