package com.example.mentbox.auth.jwt;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final CustomOAuth2User principal;

    public JwtAuthenticationToken(CustomOAuth2User principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }


    @Override
    public CustomOAuth2User getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
