package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;
import com.example.schedulerjpa.service.comment.CommentService;
import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}")
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequestDto dto,
            HttpServletRequest request
    ) {
        Long authorId = (Long) request.getSession().getAttribute(SessionConst.LOGIN_AUTHOR);
        CreateCommentResponseDto comment = commentService.createComment(dto, authorId, scheduleId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<List<CommentResponseDto>> findAllSchedule(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponseDto> response = commentService.findBySchedule_ScheduleId(scheduleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
