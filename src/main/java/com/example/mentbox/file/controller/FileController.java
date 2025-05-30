package com.example.mentbox.file.controller;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.file.dto.FileResponse;
import com.example.mentbox.file.dto.ScheduleDto;
import com.example.mentbox.file.service.FileService;
import com.example.mentbox.file.service.Schedule;
import com.example.mentbox.file.service.crud.FileCreate;
import com.example.mentbox.member.entity.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final Schedule schedule;

    @PostMapping
    public ResponseEntity<FileResponse> createFile(@Valid @RequestBody FileRequest request, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        FileResponse file = fileService.create(request, member);

        return ResponseEntity.
                status(HttpStatus.CREATED)
                .body(file);
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> readFiles(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        List<FileResponse> responses = fileService.readMemberFiles(member);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponse> readOne(@PathVariable Long fileId) {
        return fileService.readOne(fileId);
    }



    @PutMapping("/{fileId}")
    public ResponseEntity<FileResponse> updateFile(@PathVariable Long fileId, @Valid @RequestBody FileRequest request) {
        FileResponse response = fileService.update(fileId, request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        return fileService.delete(id);
    }

    @GetMapping("/api/files/schedule")
    public ResponseEntity<List<ScheduleDto>> getSchedule(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        List<ScheduleDto> response = schedule.getSchedule(member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
