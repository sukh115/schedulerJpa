package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateScheduleRequestDto {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 20, message = "제목은 20자 이하로 입력해주세요.")
    private String title;

    @Size(max = 100, message = "내용은 100자 이하로 입력해주세요.")
    private String contents;
}
