package com.example.schedulerjpa.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAuthorRequestDto {

    @NotBlank(message = "아이디를 적어주세요")
    @Size(max = 15, message = "아이디는 15자 이하로 입력해주세요")
    private String loginId;

    @NotBlank(message = "이름을 적어주세요")
    @Size(max = 5, message = "이름은 5자 이하로 입력해주세요")
    private String name;

    @NotBlank(message = "비밀번호를 적어주세요")
    @Size(min = 6, message = "비밀번호는 6자 이상 입렵해주세요")
    @Pattern(
            regexp = ".*[!@#$%^&*(),.?\":{}|<>].*",
            message = "비밀번호에는 특수문자가 최소 1개 이상 포함되어야 합니다.")
    private String password;
}
