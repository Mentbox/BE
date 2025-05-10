package com.example.mentbox.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 모든 요청을 인증 없이 허용
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        // HttpSecurity 설정을 기반으로 SecurityFilterChain 생성
        return http.build();
    }
}