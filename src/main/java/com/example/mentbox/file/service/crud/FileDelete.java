package com.example.mentbox.file.service.crud;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatFileException;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileDelete {

    private final FileRepository fileRepository;

    @Transactional
    public void deleteFile(Long id) {

        File file = fileRepository.findById(id).orElseThrow(() -> new ThereIsNotThatFileException(ErrorCode.FILE_NOT_FOUND));
        file.markDeleted();

    }

}
