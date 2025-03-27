package com.example.schedulerjpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateScheduleRequestDto {

    @NotBlank(message = "작성자 이름을 적어주세요")
    private String authorName;

    @NotBlank(message = "제목을 적어주세요")
    private String title;

    private String contents;

    public CreateScheduleRequestDto(String authorName, String title, String contents) {
        this.authorName = authorName;
        this.title = title;
        this.contents = contents;
    }
}
