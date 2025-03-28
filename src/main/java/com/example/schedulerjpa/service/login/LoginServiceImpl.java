package com.example.schedulerjpa.service.login;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.security.PasswordEncoder;
import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@link LoginService} 구현체로, 로그인 요청을 처리하고 세션에 사용자 정보를 저장
 *
 * <p>작성자 ID, 비밀번호를 검증하고 로그인에 성공하면 세션에 {@code LOGIN_AUTHOR} 속성으로 사용자 ID를 저장</p>
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인 요청을 처리
     *
     * @param dto     로그인 요청 DTO (로그인 ID, 비밀번호 포함)
     * @param request 현재 HTTP 요청 (세션을 생성하기 위해 사용)
     * @return 로그인 성공 시 작성자 정보 응답 DTO
     */
    @Override
    public LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request) {
        Author author = authorRepository.findByLoginIdOrElseThrow(dto.getLoginId());

        author.isLoginId(dto.getLoginId());
        author.verifyPassword(dto.getPassword(), passwordEncoder);


        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_AUTHOR, author.getAuthorId());

        return new LoginResponseDto(author.getAuthorId(), author.getLoginId(), author.getPassword());
    }
}
