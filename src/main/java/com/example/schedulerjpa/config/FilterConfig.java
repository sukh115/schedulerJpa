package com.example.schedulerjpa.config;

import com.example.schedulerjpa.filter.AuthFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<Filter> authoFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthFilter());   // 내가 만든 필터 등록
        registrationBean.addUrlPatterns("/");           // 모든 경로에 적용
        registrationBean.setOrder(1);                   // 필터 실행 순서

        return registrationBean;
    }
}
