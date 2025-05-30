package com.example.mentbox.member.controller;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.member.dto.MemberRequest;
import com.example.mentbox.member.dto.MemberResponse;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<MemberResponse> findOne(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Long id = oAuth2User.getMember().getId();

        return memberService.findOne(id);
    }

    @PutMapping
    public ResponseEntity<Void> update(@AuthenticationPrincipal CustomOAuth2User oAuth2User, @RequestPart("audio") MultipartFile audioFile, @RequestPart("data") MemberRequest request) {
        Member member = oAuth2User.getMember();
        return memberService.update(member, audioFile, request);

    }



}
