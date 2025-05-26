package com.example.mentbox.auth.jwt;

import com.example.mentbox.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final Member principal;

    public JwtAuthenticationToken(Member principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Member getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
