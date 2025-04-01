package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.request.UpdateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;
import com.example.schedulerjpa.dto.response.UpdateCommentReponseDto;
import com.example.schedulerjpa.service.comment.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 댓글(Comment) 관련 요청을 처리하는 컨트롤러
 *
 * <p>댓글 등록, 조회, 수정, 삭제 기능을 제공</p>
 * <p>댓글 작성 및 수정, 삭제 시 로그인된 작성자 정보를 세션에서 추출.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param scheduleId 댓글을 작성할 일정 ID
     * @param dto        댓글 생성 요청 DTO
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 추출)
     * @return 생성된 댓글 정보와 200(OK) 응답
     */
    @PostMapping("/schedules/{scheduleId}")
    public ResponseEntity<CreateCommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequestDto dto,
            HttpServletRequest request
    ) {
        Long authorId = Long.valueOf(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        CreateCommentResponseDto comment = commentService.createComment(dto, authorId, scheduleId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    /**
     * 일정에 등록된 모든 댓글을 조회합니다.
     *
     * @param scheduleId 댓글을 조회할 일정 ID
     * @return 해당 일정의 댓글 목록과 200(OK) 응답
     */
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<List<CommentResponseDto>> findAllSchedule(
            @PathVariable Long scheduleId
    ) {
        List<CommentResponseDto> response = commentService.findBySchedule_ScheduleId(scheduleId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글을 수정
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId  수정할 댓글 ID
     * @param dto        댓글 수정 요청 DTO
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 추출)
     * @return 수정된 댓글 정보와 200(OK) 응답
     */
    @PatchMapping("/schedules/{scheduleId}/{commentId}")
    public ResponseEntity<UpdateCommentReponseDto> updateComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequestDto dto,
            HttpServletRequest request
    ) {
        Long authorId = Long.valueOf(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        UpdateCommentReponseDto updateCommentReponseDto = commentService.updateComment(dto, commentId, authorId, scheduleId);

        return new ResponseEntity<>(updateCommentReponseDto, HttpStatus.OK);
    }

    /**
     * 댓글 삭제
     *
     * @param scheduleId 댓글이 속한 일정 ID
     * @param commentId  삭제할 댓글 ID
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 추출)
     * @return 200(OK) 응답 (본문 없음)
     */
    @DeleteMapping("/schedules/{scheduleId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            HttpServletRequest request
    ) {
        Long authorId = Long.valueOf(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        commentService.deleteComment(commentId, authorId, scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 해당 일정 아래 댓글 전체 삭제
     *
     * @param scheduleId 댓글이속한 일정 ID
     * @param request    현재 HTTP 요청 (세션에서 로그인 작성자 추출)
     * @return 200(OK) 응답 (본문 없음)
     */
    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> deleteAllCommentBySchedule(
            @PathVariable Long scheduleId,
            HttpServletRequest request
    ) {
        Long authorId = Long.valueOf(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
        );
        commentService.deleteALlCommentsBySchedule(scheduleId, authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
