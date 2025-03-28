package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * 일정 엔티티 클래스
 */
@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    // 일정 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    // 작성자와 연관관계 (author_id와 외래키)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    //일정 제목
    @Column(nullable = false)
    private String title;

    //일정 내용
    private String contents;

    // 기본 생성자
    public Schedule() {

    }

    /**
     * 일정 생성자
     *
     * @param author   작성자
     * @param title    일정 제목
     * @param contents 일정 내용
     */
    public Schedule(Author author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }

    /**
     * 일정 제목 및 내용을 수정합니다.
     *
     * @param title    수정할 제목
     * @param contents 수정할 내용
     */
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    /**
     * 현재 로그인한 작성자가 해당 일정의 소유자인지 검증합니다.
     *
     * @param authorId 로그인된 작성자 ID
     * @throws CustomException 작성자가 다를 경우 예외 발생
     */
    public void isAuthorId(Long authorId) {
        if (!this.author.getAuthorId().equals(authorId)) {
            throw new CustomException(ExceptionCode.AUTHOR_ID_MISMATCH);
        }
    }
}
