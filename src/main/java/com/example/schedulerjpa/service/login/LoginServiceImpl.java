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

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(LoginRequestDto dto, HttpServletRequest request) {
        Author author = authorRepository.findByLoginIdOrElseThrow(dto.getLoginId());

        author.isLoginId(dto.getLoginId());
        author.verifyPassword(dto.getPassword(), passwordEncoder);


        HttpSession session = request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_AUTHOR, author.getAuthorId());

        return new LoginResponseDto(author.getAuthorId(), author.getLoginId(),author.getPassword());
    }
}
