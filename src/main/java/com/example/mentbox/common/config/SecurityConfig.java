package com.example.mentbox.common.config;

import com.example.mentbox.auth.OAuth2.CustomOAuth2UserService;
import com.example.mentbox.auth.OAuth2.OAuth2LoginSuccessHandler;
import com.example.mentbox.auth.jwt.JwtAuthenticationFilter;
import com.example.mentbox.auth.jwt.JwtTokenProvider;
import com.example.mentbox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ✅ JWT는 무상태
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated() // 인증 필요한 API
                        .anyRequest().permitAll()) // 그 외는 모두 허용
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)) // 사용자 정보 커스텀 서비스
                        .successHandler(oAuth2LoginSuccessHandler)) // 로그인 성공 핸들러 등록
        ;

        // ✅ JWT 인증 필터 등록
        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, memberRepository),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}