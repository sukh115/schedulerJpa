package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 댓글 엔티티 클래스
 */
@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    // 댓글 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 일정 테이블과 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // 작성자 테이블과 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    // 댓글 내용
    @Column(nullable = false)
    private String content;

    // 기본 생성자
    public Comment() {

    }

    /**
     * 댓글 생성자
     *
     * @param author   댓글 작성자
     * @param schedule 댓글이 속한 일정
     * @param content  댓글 내용
     */
    public Comment(Author author, Schedule schedule, String content) {
        this.schedule = schedule;
        this.author = author;
        this.content = content;
    }

    /**
     * 댓글 내용을 수정합니다.
     *
     * @param content 수정할 댓글 내용
     */
    public void update(String content) {
        this.content = content;
    }

    /**
     * 댓글의 작성자 ID와 요청한 ID가 일치하는지 검증합니다.
     *
     * @param authorId 로그인된 작성자 ID
     * @throws CustomException 작성자가 다르면 예외 발생
     */
    public void isAuthorId(Long authorId) {
        if (!this.author.getAuthorId().equals(authorId)) {
            throw new CustomException(ExceptionCode.AUTHOR_ID_MISMATCH);
        }
    }

    /**
     * 댓글이 속한 일정 ID와 요청한 ID가 일치하는지 검증합니다.
     *
     * @param scheduleId 요청한 일정 ID
     * @throws CustomException 일정이 다르면 예외 발생
     */
    public void isScheduleId(Long scheduleId) {
        if (!this.schedule.getScheduleId().equals(scheduleId)) {
            throw new CustomException(ExceptionCode.SCHEDULE_ID_MISMATCH);
        }
    }
}
