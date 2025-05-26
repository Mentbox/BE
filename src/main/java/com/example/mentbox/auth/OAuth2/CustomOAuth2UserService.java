package com.example.mentbox.auth.OAuth2;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.NotSupportingSocialTypeException;
import com.example.mentbox.member.SocialType;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);

        String registrationId = request.getClientRegistration().getRegistrationId();
        SocialType socialType = SocialType.from(registrationId);

        Map<String, Object> attributes = oAuth2User.getAttributes();

        String socialId = extractSocialId(socialType, attributes);
        String email = extractEmail(socialType, attributes);

        Member member = memberRepository.findBySocialTypeAndSocialId(socialType, socialId)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                                .socialType(socialType)
                                .socialId(socialId)
                                .email(email)
                                .name(null)
                                .build()
                ));

        return new CustomOAuth2User(member, attributes);

    }

    private String extractSocialId(SocialType type, Map<String, Object> attributes) {

        if (type == SocialType.KAKAO) {
            return String.valueOf(attributes.get("id"));
        } else if (type == SocialType.GOOGLE) {
            return (String) attributes.get("sub");
        }

        throw new NotSupportingSocialTypeException(ErrorCode.NotSupportingSocialType);
    }

    private String extractEmail(SocialType type, Map<String, Object> attributes) {
        if (type == SocialType.KAKAO) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email");
        } else if (type == SocialType.GOOGLE) {
            return (String) attributes.get("email");
        }

        return null;
    }

}
