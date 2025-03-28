package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(nullable = false)
    private String content;

    public Comment() {

    }

    public Comment(Author author, Schedule schedule, String content) {
        this.schedule = schedule;
        this.author = author;
        this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public void isAuthorId(Long authorId) {
        if (!this.author.getAuthorId().equals(authorId)) {
            throw new CustomException(ExceptionCode.AUTHOR_ID_MISMATCH);
        }
    }

    public void isScheduleId(Long scheduleId) {
        if (!this.schedule.getScheduleId().equals(scheduleId)) {
            throw new CustomException(ExceptionCode.SCHEDULE_ID_MISMATCH);
        }
    }
}
