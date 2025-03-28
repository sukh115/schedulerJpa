package com.example.schedulerjpa.service.comment;

import com.example.schedulerjpa.dto.request.CreateCommentRequestDto;
import com.example.schedulerjpa.dto.request.UpdateCommentRequestDto;
import com.example.schedulerjpa.dto.response.CommentResponseDto;
import com.example.schedulerjpa.dto.response.CreateCommentResponseDto;
import com.example.schedulerjpa.dto.response.UpdateCommentReponseDto;

import java.util.List;

/**
 * 댓글(Comment) 관련 비즈니스 로직을 정의하는 서비스 인터페이스
 *
 * <p>댓글 생성, 조회, 수정, 삭제 기능을 제공</p>
 */
public interface CommentService {

    /**
     * 댓글을 등록
     *
     * @param dto        댓글 생성 요청 DTO
     * @param authorId   작성자 ID (세션에서 추출)
     * @param scheduleId 댓글이 속한 일정 ID
     * @return 생성된 댓글 응답 DTO
     */
    CreateCommentResponseDto createComment(CreateCommentRequestDto dto, Long authorId, Long scheduleId);

    /**
     * 특정 일정에 등록된 모든 댓글을 조회
     *
     * @param scheduleId 조회할 일정 ID
     * @return 해당 일정에 대한 댓글 응답 DTO 목록
     */
    List<CommentResponseDto> findBySchedule_ScheduleId(Long scheduleId);

    /**
     * 댓글을 수정
     *
     * @param dto        댓글 수정 요청 DTO
     * @param commentId  수정할 댓글 ID
     * @param authorId   요청한 작성자 ID
     * @param scheduleId 댓글이 속한 일정 ID
     * @return 수정된 댓글 응답 DTO
     */
    UpdateCommentReponseDto updateComment(UpdateCommentRequestDto dto, Long commentId, Long authorId, Long scheduleId);

    /**
     *댓글을 삭제
     *
     * @param commentId  삭제할 댓글 ID
     * @param authorId   작성자 ID
     * @param scheduleId 댓글이 속한 일정 ID
     */
    void deleteComment(Long commentId, Long authorId, Long scheduleId);
}
