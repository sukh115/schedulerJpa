package com.example.schedulerjpa.security;

import com.example.schedulerjpa.exception.ExceptionResponse;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse body = new ExceptionResponse(
                ExceptionCode.TOKEN_FORBIDDEN.getStatus(),   // 별도 코드 정의 권장
                ExceptionCode.TOKEN_FORBIDDEN.getCode(),
                ExceptionCode.TOKEN_FORBIDDEN.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}
