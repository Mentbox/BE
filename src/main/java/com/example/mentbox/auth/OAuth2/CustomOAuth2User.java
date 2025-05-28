package com.example.mentbox.auth.OAuth2;

import com.example.mentbox.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomOAuth2User implements OAuth2User, OidcUser {

    private final Member member;
    private final Map<String, Object> attributes;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;

    // 카카오 등 일반 OAuth2용 생성자
    public CustomOAuth2User(Member member, Map<String, Object> attrs) {
        this(member, attrs, null, null);
    }

    // 구글 OIDC용 생성자
    public CustomOAuth2User(Member member, Map<String, Object> attrs,
                            OidcIdToken idToken, OidcUserInfo userInfo) {
        this.member = member;
        this.attributes = attrs;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    @Override
    public Map<String, Object> getClaims() {
        return idToken != null ? idToken.getClaims() : attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getName() {
        return member.getId().toString();
    }

    @Override
    public OidcIdToken getIdToken() {
        return idToken;
    }


    @Override
    public OidcUserInfo getUserInfo() {
        return userInfo;
    }

    public Member getMember() {
        return member;
    }
}
