package com.example.mentbox.file.service;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatFileException;
import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUpdate {

    private final FileRepository fileRepository;

    @Transactional
    public ResponseEntity<FileResponse> updateFile(Long id, FileRequest dto) {

        File file = fileRepository.findById(id).orElseThrow(() -> new ThereIsNotThatFileException(ErrorCode.FILE_NOT_FOUND));

        file.update(dto);

        return ResponseEntity.ok(FileResponse.from(file));
    }
}
