package com.example.mentbox.recording.service.crud;

import com.example.mentbox.common.exception.AccessDeniedException;
import com.example.mentbox.common.exception.AudioFileEmptyException;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatFileException;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingRequest;
import com.example.mentbox.recording.dto.RecordingResponse;
import com.example.mentbox.recording.entity.AudioFile;
import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.repository.RecordingRepository;
import com.example.mentbox.recording.utility.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecordingCreate {

    private final RecordingRepository recordingRepository;
    private final S3Uploader s3Uploader;
    private final FileRepository fileRepository;

    @Transactional
    public RecordingResponse createRecording(Long fileId, MultipartFile audioFile, RecordingRequest request, Member member) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new ThereIsNotThatFileException(ErrorCode.FILE_NOT_FOUND));


        if (audioFile == null || audioFile.isEmpty()) {
            throw new AudioFileEmptyException(ErrorCode.AudioFileEmptyException);
        }

        if (!file.getMember().equals(member)) {
            throw new AccessDeniedException(ErrorCode.AccessDenied);
        }

        Recording recording = Recording.builder()
                .feedBack(request.getFeedBack())
                .build();

        recording.setScore(request.getScore().toEntity());

        AudioFile voiceFile = initAudioFile(audioFile, member);

        recording.setAudioFile(voiceFile);

        recording.setFile(file);

        recordingRepository.save(recording);

        return RecordingResponse.from(recording, s3Uploader, voiceFile.getKey());

    }

    private AudioFile initAudioFile(MultipartFile audioFile, Member member) {

        String key = s3Uploader.upload(audioFile, "/recordings");
        String originalFilename = audioFile.getOriginalFilename();
        long fileSize = audioFile.getSize();
        String contentType = audioFile.getContentType();
        Member uploadedBy = member;

        return AudioFile.builder()
                .key(key)
                .originalFileName(originalFilename)
                .fileSize(fileSize)
                .contentType(contentType)
                .uploadedBy(uploadedBy)
                .build();

    }
}
