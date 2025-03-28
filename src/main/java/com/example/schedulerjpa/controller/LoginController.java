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

/**
 * 로그인 및 로그아웃 기능을 담당하는 컨트롤러입니다.
 *
 * <p>세션 기반 인증을 사용하며, 로그인 시 세션에 작성자 정보를 저장하고
 * 로그아웃 시 세션을 무효화합니다.</p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    /**
     * 로그아웃 요청을 처리
     *
     * @param dto     로그인 요청 DTO (로그인 ID, 비밀번호 포함)
     * @param request 현재 HTTP 요청 (세션에 작성자 정보를 저장하기 위해 사용)
     * @return 로그인 성공 시 작성자 정보와 200(OK) 응답
     */
    @PostMapping
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto,
            HttpServletRequest request
    ) {
        LoginResponseDto login = loginService.login(dto, request);
        return ResponseEntity.ok(login);
    }

    /**
     * @param request 현재 HTTP 요청
     * @return 로그아웃 메시지와 200(OK) 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 이미 로그아웃 된 상태라면 예외를 발생
        if (session == null || session.getAttribute(SessionConst.LOGIN_AUTHOR) == null) {
            throw new CustomException(ExceptionCode.ALREADY_LOGOUT);
        }

        session.invalidate(); // 로그아웃

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
