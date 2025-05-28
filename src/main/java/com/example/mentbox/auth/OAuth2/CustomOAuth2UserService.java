package com.example.mentbox.auth.OAuth2;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.NotSupportingSocialTypeException;
import com.example.mentbox.member.SocialType;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oauth2User = delegate.loadUser(userRequest);
        Map<String, Object> attrs = oauth2User.getAttributes();

        String socialId = String.valueOf(attrs.get("id"));
        Member member = memberRepository.findBySocialTypeAndSocialId(SocialType.KAKAO, socialId)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .socialType(SocialType.KAKAO)
                                .socialId(socialId)
                                .name(null)
                                .build()));

        return new CustomOAuth2User(member, attrs);
    }


}
