package com.example.schedulerjpa.config;

import com.example.schedulerjpa.filter.JwtAuthenticationFilter;
import com.example.schedulerjpa.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity // 스프링시큐리티 기능 활성화
@AllArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * securityFilterChain 빈 정의
     *
     * @param http HttpSecurity 객체
     * @return SecurityFilterChain 객체
     * @throws Exception 설정 중 오류 발생 시 예외
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 모든 요청에 대해 보안 정책을 적용함 (securityMatcher 선택적)
                .securityMatcher((request -> true))

                // CSRF 보호 비활성화 (JWT 세션을 사용하지 않기 때문에 필요 없음)
                .csrf(AbstractHttpConfigurer::disable)

                // 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/authors/signup",
                                "/schedules",                      // 전체 조회
                                "/schedules/paged",                // 페이징 조회
                                "/schedules/search",               // 검색
                                "/schedules/{scheduleId}",         // 단건 조회
                                "/comments/schedules/**"           // 댓글 전체 조회
                        ).permitAll()
                        .anyRequest().authenticated()
                )


                // 커스텀 JWT 인증 필터 등록
                // UsernamePasswordAuthenticationFilter 전에 실행되어 JWT 검증 처리
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)

                // 최종 SecurityFilterChain 반환
                .build();
    }
}