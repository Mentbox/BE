package com.example.mentbox.auth.service;

import com.example.mentbox.auth.dto.SignupDetailRequest;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatMemberException;
import com.example.mentbox.interest.KeywordType;
import com.example.mentbox.interest.entity.Interest;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupDetail {

    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<Void> signup(Member member, SignupDetailRequest dto, MultipartFile imageFile) {
        member.setName(dto.getName());
        List<String> keywords = dto.getKeywords();

        List<Interest> interests = new ArrayList<>();

        memberRepository.findById(member.getId()).orElseThrow(() -> new ThereIsNotThatMemberException(ErrorCode.ThereIsNotThatMember));

        for (String keyword : keywords) {
            KeywordType keywordType = KeywordType.stringToEnum(keyword);
            Interest interest = Interest.of(keywordType, member);
            interests.add(interest);
        }

        member.setInterests(interests);

        if (imageFile != null && !imageFile.isEmpty()) {
            String key = s3Uploader.upload(imageFile, "/profile_img");
            member.setProfileImageKey(key);
        }
        memberRepository.save(member);


        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
