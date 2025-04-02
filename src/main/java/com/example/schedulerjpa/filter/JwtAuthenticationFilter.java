package com.example.schedulerjpa.filter;

import com.example.schedulerjpa.exception.ExceptionResponse;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * JWY 인증 필터
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
    }


    /**
     * JWT 인증 처리
     *
     * @param request     HTTP 요청 객체
     * @param response    HTTP 응답 객체
     * @param filterChain 필터 체인
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Authorization 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 토큰이 없으면 다음 필터로 진행
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // 토큰 유효성 검사 및 사용자 ID 추출
            if (jwtTokenProvider.validateToken(token)) {
                String authorId = jwtTokenProvider.getAuthorId(token);
                // 사용자 인증 객체 생성 후 SecurityContext에 설정
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(authorId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
                // 유효하지 않은 토큰
                handleException(response, ExceptionCode.TOKEN_INVALID);
                return;
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            handleException(response, ExceptionCode.TOKEN_EXPIRED);
            return;
        } catch (io.jsonwebtoken.security.SecurityException e) {
            handleException(response, ExceptionCode.TOKEN_SIGNATURE_INVALID);
            return;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            handleException(response, ExceptionCode.TOKEN_MALFORMED);
            return;
        } catch (Exception e) {
            handleException(response, ExceptionCode.TOKEN_INVALID);
            return;
        }

        // 다음 필터로 진행
        filterChain.doFilter(request, response);
    }

    /**
     * 인증 실패 시 JSON 에러 응답 반환
     *
     * @param response 응답 객체
     * @param error    에러 코드 (ExceptionCode)
     * @throws IOException 응답 출력 오류
     */
    private void handleException(HttpServletResponse response, ExceptionCode error) throws IOException {
        response.setStatus(error.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ExceptionResponse body = new ExceptionResponse(
                error.getStatus(),
                error.getCode(),
                error.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        new ObjectMapper().writeValue(response.getWriter(), body);
    }

    /**
     * Authorization 헤더에서 JWT 토큰 추출
     *
     * @param request 요청 객체
     * @return Bearer 토큰 문자열 또는 null
     */
    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }


}
