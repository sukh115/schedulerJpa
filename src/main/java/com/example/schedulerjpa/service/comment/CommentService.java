package com.example.schedulerjpa.service.comment;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;

public interface CommentService {
    CreateCommentResponseDto createComment(CreateCommentRequestDto dto, Long authorId, Long scheduleId);
}
