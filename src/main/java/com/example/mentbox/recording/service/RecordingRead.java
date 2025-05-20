package com.example.mentbox.recording.service;

import com.example.mentbox.common.exception.AccessDeniedException;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.MentboxServiceException;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingDetailResponse;
import com.example.mentbox.recording.dto.RecordingResponse;
import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.entity.Score;
import com.example.mentbox.recording.repository.RecordingRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordingRead {

    private final RecordingRepository recordingRepository;
    private final S3Uploader s3Uploader;
    private final FileRepository fileRepository;

    public RecordingDetailResponse getOne(Long recordingId, Member member) {

        Recording recording = recordingRepository.findById(recordingId)
                .orElseThrow(() -> new MentboxServiceException(ErrorCode.RECORDING_NOT_FOUND));


        if (!recording.getFile().getMember().getId().equals(member.getId())) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        File file = recording.getFile();
        Score score = recording.getScore();

        return RecordingDetailResponse.builder()
                .fileName(file.getTitle())
                .targetDate(file.getTargetDate())
                .audioUrl(s3Uploader.generatePresignedUrl(recording.getAudioFile().getKey()))
                .feedback(recording.getFeedBack())
                .accuracy(score.getAccuracy())
                .pronunciation(score.getPronunciation())
                .intonation(score.getIntonation())
                .build();

    }
}
