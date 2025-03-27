package com.example.schedulerjpa.entity;

import com.example.schedulerjpa.dto.CreateScheduleRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "simple_schedule")
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;

    @Column(nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String title;

    private String contents;

    public Schedule(CreateScheduleRequestDto dto) {
        this.authorName = dto.getAuthorName();
        this.title = dto.getTitle();
        this.contents = dto.getContents();
    }
    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

}
