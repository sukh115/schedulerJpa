package com.example.schedulerjpa.config;

import com.example.schedulerjpa.filter.AuthFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 필터 설정 클래스
@Configuration
public class FilterConfig {

    // AuthFilter를 등록하는 빈 설정입니다.
    @Bean
    public FilterRegistrationBean<Filter> authorFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter());   // 내가 만든 필터 등록
        registrationBean.addUrlPatterns("/");           // 모든 경로에 적용
        registrationBean.setOrder(1);                   // 필터 실행 순서

        return registrationBean;
    }
}
