package com.example.mentbox.recording.controller;

import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recordings")
public class RecordingController {

    private final RecordingService recordingService;

    @PostMapping(value = "/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createRecording(
            @PathVariable Long fileId,
            @RequestPart("audio") MultipartFile audioFile,
            @RequestPart("data") @Valid RecordingRequest request,
            @AuthenticationPrincipal Member member
    ) {
        Long recordingId = recordingService.createRecording(fileId, audioFile, request, member);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
