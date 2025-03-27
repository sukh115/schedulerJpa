package com.example.schedulerjpa.service.login;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request);
}
