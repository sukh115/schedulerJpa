package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentResponseDto {
    private final Long commentId;
    private final String authorName;
    private final String scheduleTitle;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;
}
