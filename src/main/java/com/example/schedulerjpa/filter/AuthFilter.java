package com.example.schedulerjpa.filter;

import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.Filter;

import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        // 로그인/회원가입은 인증 없이 통과
        if (requestURI.startsWith("/login")
                || requestURI.startsWith("/authors")
                || requestURI.startsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        // 세션 검사 (없으면 로그인 X)
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_AUTHOR) == null) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().write("로그인이 필요합니다");
            return;
        }

        // 로그인 상태 >> 다음필터 or 컨틀로러로 전달
        chain.doFilter(request, response);
    }
}
