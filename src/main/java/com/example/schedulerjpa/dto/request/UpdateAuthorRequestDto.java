package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAuthorRequestDto {

    @NotBlank(message = "아이디를 적어주세요")
    private String loginId;

    @NotBlank(message = "이름을 적어주세요")
    private String name;

}
