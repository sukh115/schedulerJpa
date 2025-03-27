package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.service.login.LoginService;
import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {
        LoginResponseDto login = loginService.login(dto, request);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_AUTHOR) == null) {
            throw new CustomException(ExceptionCode.ALREADY_LOGGEFOUT);
        }

        session.invalidate();

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
