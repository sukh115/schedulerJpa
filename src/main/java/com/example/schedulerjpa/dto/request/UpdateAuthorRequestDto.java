package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateAuthorRequestDto {

    @NotBlank(message = "아이디를 적어주세요")
    @Size(max = 15, message = "아이디는 15자 이하로 입력해주세요")
    private String loginId;

    @NotBlank(message = "이름을 적어주세요")
    @Size(max = 5, message = "이름은 5자 이하로 입력해주세요")
    private String name;

}
