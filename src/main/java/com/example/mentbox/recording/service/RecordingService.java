package com.example.mentbox.recording.service;

import com.example.mentbox.member.entity.Member;
import com.example.mentbox.recording.dto.RecordingDetailResponse;
import com.example.mentbox.recording.dto.RecordingRequest;
import com.example.mentbox.recording.dto.RecordingResponse;
import com.example.mentbox.recording.service.crud.RecordingCreate;
import com.example.mentbox.recording.service.crud.RecordingDelete;
import com.example.mentbox.recording.service.crud.RecordingRead;
import com.example.mentbox.recording.service.crud.RecordingUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class RecordingService {

    private final RecordingCreate recordingCreate;
    private final RecordingRead recordingRead;
    private final RecordingUpdate recordingUpdate;
    private final RecordingDelete recordingDelete;


    public RecordingResponse create(Long fileId, MultipartFile audioFile, RecordingRequest request, Member member) {
        return recordingCreate.createRecording(fileId, audioFile, request, member);
    }

    public RecordingDetailResponse read(Long recordingId, Member member) {
        return recordingRead.getOne(recordingId, member);
    }

    public RecordingResponse update(Long recordingId, RecordingRequest request, Member member) {
        return recordingUpdate.update(recordingId, request, member);
    }

    public void delete(Long recordingId, Member member) {
        recordingDelete.delete(recordingId, member);
    }


}
