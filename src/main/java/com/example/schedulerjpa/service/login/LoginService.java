package com.example.schedulerjpa.service.login;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import com.example.schedulerjpa.dto.response.TokenResponseDto;

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

    /**
     * 토큰 재발행 요청을 처리
     *
     * @param refreshToken 요청 헤더에서 추출한 Refresh Token
     * @return 새로 발급된 Access Token + 기존 Refresh Token
     */
    TokenResponseDto reissue(String refreshToken);

    /**
     * 로그아웃 요청을 처리
     *
     * @param refreshToken 요청 헤더에서 Token
     */
    void logout(String refreshToken);
}
