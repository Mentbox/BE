package com.example.mentbox.member.service;

import com.example.mentbox.member.dto.MemberRequest;
import com.example.mentbox.member.dto.MemberResponse;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.service.crud.MemberRead;
import com.example.mentbox.member.service.crud.MemberUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRead memberRead;
    private final MemberUpdate memberUpdate;
    public ResponseEntity<MemberResponse> findOne(Long memberId) {
        MemberResponse response = memberRead.findOne(memberId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<Void> update(Member member, MultipartFile audioFile, MemberRequest request) {
        memberUpdate.update(member, audioFile, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
