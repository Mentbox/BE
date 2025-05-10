package com.example.mentbox.file.service;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.NoFilesForMemberException;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.dto.ScheduleDto;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileRead {

    private final FileRepository fileRepository;

    public List<FileResponse> readAllFiles(Member member) {
        List<File> files = fileRepository.findByMember(member);

        if (files.isEmpty()) {
            throw new NoFilesForMemberException(ErrorCode.NoFilesForMemberException);
        }

        List<FileResponse> responses = new ArrayList<>();

        for (File file : files) {
            FileResponse dto = FileResponse.from(file);
            responses.add(dto);
        }

        return responses;

    }

}
