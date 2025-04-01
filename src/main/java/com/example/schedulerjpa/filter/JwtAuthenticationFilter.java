package com.example.schedulerjpa.filter;

import com.example.schedulerjpa.exception.ExceptionResponse;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import com.example.schedulerjpa.security.JwtTokenProvider;
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

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (jwtTokenProvider.validateToken(token)) {
                String authorId = jwtTokenProvider.getAuthorId(token);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(authorId, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } else {
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

        filterChain.doFilter(request, response);
    }

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

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }


}
