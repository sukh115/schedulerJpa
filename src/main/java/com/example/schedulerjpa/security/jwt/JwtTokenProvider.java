package com.example.schedulerjpa.security.jwt;

import com.example.schedulerjpa.dto.response.TokenResponseDto;
import com.example.schedulerjpa.exception.CustomException;
import com.example.schedulerjpa.exception.exceptionCode.ExceptionCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JWT 토큰 발급 및 검증 유틸리티
 */
@Component
@NoArgsConstructor
public class JwtTokenProvider {

    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long accessTokenExpireMs = 1000 * 60 * 30;
    private long refreshTokenExpireMs = 1000L * 60 * 60 * 24 * 7;


    /**
     * JWT AccessToken 생성
     *
     * @param authorId 작성자 ID
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateAccessToken(String authorId) {
        return Jwts.builder()
                .setSubject(authorId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireMs))
                .signWith(secretKey)
                .compact();
    }

    /**
     * JWT RefreshToken 생성
     *
     * @param authorId 작성자 ID
     * @return 생성된 JWT 토큰 문자열
     */
    public String generateRefreshToken(String authorId) {
        return Jwts.builder()
                .setSubject(authorId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpireMs))
                .signWith(secretKey)
                .compact();
    }

    /**
     * AccessToken, RefreshToken 동시 생성
     *
     * @param authorId 작성자 ID
     * @return TokenResponseDto
     */
    public TokenResponseDto generateTokenPair(String authorId) {
        String accessToken = generateAccessToken(authorId);
        String refreshToken = generateRefreshToken(authorId);
        return new TokenResponseDto(accessToken, refreshToken);
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
     * 토큰에 있는 만료시간 가져오기
     *
     * @param token JWT 토큰
     * @return 만료시간
     */
    public long getRemainingExpiration(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.getTime() - System.currentTimeMillis();
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
