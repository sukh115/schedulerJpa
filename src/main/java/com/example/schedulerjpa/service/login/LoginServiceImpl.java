package com.example.schedulerjpa.service.login;

import com.example.schedulerjpa.dto.request.LoginRequestDto;
import com.example.schedulerjpa.dto.response.LoginResponseDto;
import com.example.schedulerjpa.dto.response.TokenResponseDto;
import com.example.schedulerjpa.entity.Author;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.repository.AuthorRepository;
import com.example.schedulerjpa.security.PasswordEncoder;
import com.example.schedulerjpa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 로그인 요청을 처리
     *
     * @param dto 로그인 요청 DTO (로그인 ID, 비밀번호 포함)
     * @return 로그인 성공 시 작성자 정보 응답 DTO
     */
    @Override
    public LoginResponseDto login(LoginRequestDto dto) {
        Author author = authorRepository.findByLoginIdOrElseThrow(dto.getLoginId());

        author.isLoginId(dto.getLoginId());
        author.verifyPassword(dto.getPassword(), passwordEncoder);

        //JWT 발급
        TokenResponseDto tokenPair = jwtTokenProvider.generateTokenPair(author.getAuthorId().toString());

        // Refresh Token Redis 저장
        redisTemplate.opsForValue().set(
                "RT:" + author.getAuthorId(),
                tokenPair.getRefreshToken(),
                7, TimeUnit.DAYS
        );

        return new LoginResponseDto(author.getAuthorId(), author.getName(), author.getLoginId(), tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }

    @Override
    public TokenResponseDto reissue(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }

        String authorId = jwtTokenProvider.getAuthorId(refreshToken);
        String stored = redisTemplate.opsForValue().get("RT:" + authorId);

        if (stored == null || !stored.equals(refreshToken)) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }

        String newAccessToken = jwtTokenProvider.generateAccessToken(authorId);
        return new TokenResponseDto(newAccessToken, refreshToken);
    }

    @Override
    public void logout(String refreshToken) {
        // 토큰 유효성 검사
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }

        // 작성자 ID 추출
        String authorId = jwtTokenProvider.getAuthorId(refreshToken);

        // Redis에서 Refresh Token 삭제
        redisTemplate.delete("RT:" + authorId);
    }

    }


