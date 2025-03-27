package com.example.schedulerjpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(nullable = false)
    private String title;

    private String contents;

    public Schedule() {

    }

    public Schedule(Author author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }
}
