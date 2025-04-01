package com.example.schedulerjpa.service.login;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;

/**
 * 로그인 처리를 위한 서비스 인터페이스입니다.
 */
public interface LoginService {
    /**
     * 로그인 요청을 처리
     *
     * @param dto 로그인 요청 DTO (로그인 ID, 비밀번호 포함)
     * @return 로그인 성공 시 작성자 정보 응답 DTO
     */
    LoginResponseDto login(LoginRequestDto dto);
}
