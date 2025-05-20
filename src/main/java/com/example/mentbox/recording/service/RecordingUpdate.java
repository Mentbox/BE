package com.example.mentbox.recording.service;

import com.example.mentbox.common.exception.AccessDeniedException;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatRecording;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingRequest;
import com.example.mentbox.recording.dto.RecordingResponse;
import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.repository.RecordingRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordingUpdate {

    private final S3Uploader s3Uploader;
    private final RecordingRepository recordingRepository;


    public RecordingResponse update(Long recordingId, RecordingRequest request, Member member) {
        Recording recording = recordingRepository.findById(recordingId).orElseThrow(() -> new ThereIsNotThatRecording(ErrorCode.RECORDING_NOT_FOUND));

        File file = recording.getFile();

        if (!file.getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        recording.update(request);

        String key = recording.getAudioFile().getKey();

        return RecordingResponse.from(recording, s3Uploader, key);

    }
}
