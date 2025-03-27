package com.example.schedulerjpa.service;

import com.example.schedulerjpa.dto.LoginRequestDto;
import com.example.schedulerjpa.dto.LoginResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface LoginService {
    LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request);
}
