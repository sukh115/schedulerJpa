package com.example.schedulerjpa.repository;

import com.example.schedulerjpa.entity.Comment;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 댓글(Comment) 엔티티에 대한 JPA 리포지토리 인터페이스입니다.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 댓글 ID로 댓글을 조회
     * 존재하지 않을 경우 {@link CustomException}을 발생
     *
     * @param commentId 조회할 댓글 ID
     * @return 댓글 엔티티
     * @throws CustomException 존재하지 않는 경우 COMMENT_NOT_FOUND 예외 발생
     */
    default Comment findByIdOrElseThrow(Long commentId) {
        return findById(commentId).orElseThrow(() -> new CustomException(ExceptionCode.COMMENT_NOT_FOUND));
    }

    /**
     * 특정 일정에 등록된 모든 댓글을 조회
     *
     * @param scheduleId 일정 ID
     * @return 댓글 목록
     */
    List<Comment> findBySchedule_ScheduleId(Long scheduleId);
}
