package com.example.mentbox.auth.controller;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.auth.dto.SignupDetailRequest;
import com.example.mentbox.auth.service.SignupDetail;
import com.example.mentbox.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class SignupController {

    private final SignupDetail signupDetail;


    @PostMapping("/signup-details")
    public ResponseEntity<Void> signup(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @RequestBody @Valid SignupDetailRequest request) {
        Member member = oAuth2User.getMember();

        return signupDetail.signup(member, request);

    }
}
