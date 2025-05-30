package com.example.mentbox.member.service.crud;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatMemberException;
import com.example.mentbox.interest.KeywordType;
import com.example.mentbox.interest.entity.Interest;
import com.example.mentbox.member.dto.MemberRequest;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberUpdate {

    private final S3Uploader s3Uploader;
    private final MemberRepository memberRepository;

    @Transactional
    public void update(Member member, MultipartFile audioFile, MemberRequest dto) {

        memberRepository.findById(member.getId()).orElseThrow(() -> new ThereIsNotThatMemberException(ErrorCode.ThereIsNotThatMember));

        member.setName(dto.getName());
        List<String> keywords = dto.getInterests();

        List<Interest> interests = new ArrayList<>();

        for (int i = 0; i < keywords.size(); i++) {
            if (keywords.get(i).length() != 0) {
                KeywordType keywordType = KeywordType.stringToEnum(keywords.get(i));
                Interest interest = Interest.of(keywordType, member);
                interests.add(interest);
            }
        }

        member.setInterests(interests);

        String profileImageKey = member.getProfileImageKey();

        if (profileImageKey != null) {
            s3Uploader.delete(profileImageKey);
        }

        if (!audioFile.isEmpty() && audioFile != null) {
            String key = s3Uploader.upload(audioFile, "/profile_img");
            member.setProfileImageKey(key);
        }
    }
}
