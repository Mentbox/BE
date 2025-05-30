package com.example.mentbox.auth.OAuth2;

import com.example.mentbox.member.SocialType;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final MemberRepository memberRepository;
    private final OidcUserService delegate = new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest request) {
        OidcUser oidcUser = delegate.loadUser(request);
        Map<String, Object> attrs = oidcUser.getAttributes();

        String socialId = (String) attrs.get("sub");
        Member member = memberRepository.findBySocialTypeAndSocialId(SocialType.GOOGLE, socialId)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .socialType(SocialType.GOOGLE)
                                .socialId(socialId)
                                .name(null)
                                .build()));

        return new CustomOAuth2User(member, attrs, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }

}
