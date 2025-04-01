package com.example.schedulerjpa.security;

import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰 발급 및 검증 유틸리티
 */
@Component
public class JwtTokenProvider {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expireTimeMs = 1000 * 60 * 30;

    /**
     * JWT 생성
     *
     * @param authorId 작성자 ID
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateToken(String authorId) {
        return Jwts.builder()
                .setSubject(authorId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(secretKey)
                .compact();
    }

    /**
     * @param token JWT 토큰
     * @return 추출된 작성자 ID
     */
    public String getAuthorId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * JWT 토큰의 유효성 검증
     *
     * @param token JWT 토큰
     * @return 유효한면 true 아니면 @exception TOKEN_INVALID
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ExceptionCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new CustomException(ExceptionCode.TOKEN_MALFORMED);
        } catch (SecurityException | MalformedJwtException e) {
            throw new CustomException(ExceptionCode.TOKEN_SIGNATURE_INVALID);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ExceptionCode.TOKEN_INVALID);
        }
    }

}
