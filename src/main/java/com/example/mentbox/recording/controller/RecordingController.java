package com.example.mentbox.recording.controller;

import com.example.mentbox.auth.OAuth2.CustomOAuth2User;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingDetailResponse;
import com.example.mentbox.recording.dto.RecordingRequest;
import com.example.mentbox.recording.dto.RecordingResponse;
import com.example.mentbox.recording.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recordings")
public class RecordingController {

    private final RecordingService recordingService;

    @PostMapping(value = "/{fileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecordingResponse> createRecording(
            @PathVariable Long fileId,
            @RequestPart("audio") MultipartFile audioFile,
            @RequestPart("data") @Valid RecordingRequest request,
            @AuthenticationPrincipal CustomOAuth2User oAuth2User
            ) {
        Member member = oAuth2User.getMember();
        RecordingResponse response = recordingService.create(fileId, audioFile, request, member);
        // 녹음 리소스 조회용 URI
        URI location = URI.create("/api/recordings/" + response.getId());
        return ResponseEntity
                .created(location)  // 201 + Location: /api/recordings/{saved.getId()}
                .body(response);
    }

    @GetMapping("/{recording_id}")
    public ResponseEntity<RecordingDetailResponse> readRecroding(@PathVariable Long recordingId, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        RecordingDetailResponse response = recordingService.read(recordingId, member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("{recording_id}")
    public ResponseEntity<RecordingResponse> updateRecording(@PathVariable Long recording_id, @RequestBody RecordingRequest request, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        RecordingResponse response = recordingService.update(recording_id, request, member);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("{recordingId}")
    public ResponseEntity<Void> deleteRecording(@PathVariable Long recordingId, @AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        Member member = oAuth2User.getMember();
        recordingService.delete(recordingId, member);

        return ResponseEntity.noContent().build();
    }


}
