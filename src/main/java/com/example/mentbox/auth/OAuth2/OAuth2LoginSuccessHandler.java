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


        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        Member member = oAuth2User.getMember();


        String accessToken = jwtTokenProvider.createAccessToken(member.getId());

        String redirectUrl;
        if (member.getName() == null) {
            redirectUrl = "https://localhost:8080/signup-details";
        } else {
            redirectUrl = "https://localhost:8080";
        }

        // ✅ 쿼리 파라미터로 토큰 전달
        response.sendRedirect(redirectUrl + "?token=" + accessToken);
    }
}
