package com.example.schedulerjpa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateCommentReponseDto {
    private final String authorName;
    private final String content;
    private final LocalDateTime updatedDate;
}
