package com.example.schedulerjpa.service.comment;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.request.UpdateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;
import com.example.schedulerjpa.dto.response.UpdateCommentReponseDto;

import java.util.List;

public interface CommentService {
    CreateCommentResponseDto createComment(CreateCommentRequestDto dto, Long authorId, Long scheduleId);

    List<CommentResponseDto> findBySchedule_ScheduleId(Long ScheduleId);

    UpdateCommentReponseDto updateComment(UpdateCommentRequestDto dto, Long commentId, Long authorId, Long scheduleId);
}
