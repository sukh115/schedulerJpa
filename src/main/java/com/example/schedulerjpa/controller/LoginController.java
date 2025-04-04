package com.example.schedulerjpa.controller;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import com.example.schedulerjpa.dto.response.TokenResponseDto;
import com.example.schedulerjpa.service.login.LoginService;
import com.example.schedulerjpa.util.AuthUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
     * @param dto 로그인 요청 DTO (로그인 ID, 비밀번호 포함)
     * @return 로그인 성공 시 작성자 정보와 200(OK) 응답
     */
    @PostMapping
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto
    ) {
        LoginResponseDto login = loginService.login(dto);
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    /**
     * 액세스 토큰 재발급
     *
     * @param request
     * @return 토큰 재발행 성공 시 토큰 정보와 200(OK) 응답
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(HttpServletRequest request) {
        String token = AuthUtil.resolveToken(request);
        TokenResponseDto reissued = loginService.reissue(token);
        return new ResponseEntity<>(reissued, HttpStatus.OK);
    }

    /**
     * 로그아웃
     *
     * @param request
     * @return  로그아웃 성공 시 200(OK) 응답
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = AuthUtil.resolveToken(request);
        loginService.logout(token);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
