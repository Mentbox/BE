package com.example.mentbox.file.controller;

import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.service.FileCreate;
import com.example.mentbox.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileCreate fileCreate;

    @PostMapping
    public ResponseEntity<FileResponse> createFile(@Valid @RequestBody FileRequest request) {
        FileResponse file = fileCreate.createFile(request);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(file);


    }

}
