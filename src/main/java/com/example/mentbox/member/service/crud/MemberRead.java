package com.example.mentbox.member.service.crud;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatMemberException;
import com.example.mentbox.interest.entity.Interest;
import com.example.mentbox.member.dto.MemberResponse;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberRead {

    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    public MemberResponse findOne(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new ThereIsNotThatMemberException(ErrorCode.ThereIsNotThatMember));

        MemberResponse response = new MemberResponse();
        response.setName(member.getName());
        List<Interest> interests = member.getInterests();
        List<String> ret = new ArrayList<>();

        for (Interest interest : interests) {
            ret.add(interest.getKeyword().getDisplayName());
        }
        response.setInterests(ret);

        String ImageKey = member.getProfileImageKey();

        if (ImageKey == null) {
            response.setProfileUrl("이미지 없음");
        } else {
            response.setProfileUrl(s3Uploader.generatePresignedUrl(ImageKey));
        }

        return response;


    }
}
