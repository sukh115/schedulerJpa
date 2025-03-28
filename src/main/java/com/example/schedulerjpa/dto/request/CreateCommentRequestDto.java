package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {
    @NotBlank(message = "내용을 적어주세요")
    @Size(max = 100, message = "내용은 100자 이하로 적어주세요")
    private String content;
}
