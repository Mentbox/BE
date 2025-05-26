package com.example.mentbox.auth.OAuth2;

import com.example.mentbox.auth.jwt.JwtTokenProvider;
import com.example.mentbox.member.entity.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 🔹 로그인 성공한 사용자 정보 꺼냄
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Member member = oAuth2User.getMember();

        // 🔹 JWT 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());

        // 🔹 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        // 🔹 토큰을 JSON으로 응답 (프론트가 쉽게 처리하게)
        response.getWriter().write("{\"accessToken\": \"" + accessToken + "\"}");
    }
}
