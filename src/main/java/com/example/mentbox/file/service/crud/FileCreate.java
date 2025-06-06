package com.example.mentbox.file.service.crud;

import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileCreate {

    private final FileRepository fileRepository;

    @Transactional
    public FileResponse createFile(FileRequest request, Member member) {
        File file = request.toEntity();

        file.setMember(member);

        fileRepository.save(file);

        return FileResponse.from(file);

    }

}
