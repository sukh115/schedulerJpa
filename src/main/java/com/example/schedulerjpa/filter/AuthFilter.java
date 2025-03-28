package com.example.schedulerjpa.filter;

import com.example.schedulerjpa.session.SessionConst;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

// 인증 필터 클래스
public class AuthFilter implements Filter {

    /**
     * 요청이 필터를 통과하기 전 실행되는 메서드입니다.
     * 로그인/회원가입/로그아웃 URL은 예외 처리하고,
     * 그 외 요청에 대해 세션 기반 인증을 수행합니다.
     *
     * @param request  클라이언트의 요청
     * @param response 서버의 응답
     * @param chain    다음 필터 또는 서블릿으로의 체인
     * @throws IOException      입출력 예외
     * @throws ServletException 서블릿 예외
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestURI = httpServletRequest.getRequestURI();

        // 로그인/회원가입/로그아웃은 인증 없이 허용
        if (requestURI.startsWith("/login")
                || requestURI.startsWith("/authors")
                || requestURI.startsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        // 세션 확인 및 로그인 여부 검사
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
