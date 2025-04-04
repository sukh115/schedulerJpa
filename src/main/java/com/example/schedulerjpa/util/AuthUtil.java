package com.example.schedulerjpa.util;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
    public static Long getLoginAuthorId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof String str) || str.equals("anonymousUser")) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }

        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }
    }

    public static String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer")) {
            return bearer.substring(7);
        }
        return null;
    }
}
