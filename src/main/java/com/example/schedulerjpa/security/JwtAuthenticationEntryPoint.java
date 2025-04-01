package com.example.schedulerjpa.security;

import com.example.schedulerjpa.exception.ExceptionResponse;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse body = new ExceptionResponse(
                ExceptionCode.TOKEN_INVALID.getStatus(),
                ExceptionCode.TOKEN_INVALID.getCode(),
                ExceptionCode.TOKEN_INVALID.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}