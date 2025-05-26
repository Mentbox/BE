package com.example.mentbox.recording.service.crud;

import com.example.mentbox.common.exception.AccessDeniedException;
import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.ThereIsNotThatRecording;
import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.repository.RecordingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecordingDelete {

    private final RecordingRepository recordingRepository;

    public void delete(Long recordingId, Member member) {
        Recording recording = recordingRepository.findById(recordingId).orElseThrow(() -> new ThereIsNotThatRecording(ErrorCode.RECORDING_NOT_FOUND));

        if (!recording.getFile().getMember().getId().equals(member.getId()))
            throw new AccessDeniedException(ErrorCode.AccessDenied);

        recording.markDeleted();


    }

}
