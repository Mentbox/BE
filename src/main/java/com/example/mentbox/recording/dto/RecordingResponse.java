package com.example.mentbox.recording.dto;

import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.utility.S3Uploader;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RecordingResponse {
    private Long id;
    private String feedBack;
    private String audioUrl;
    private Integer accuracy;
    private Integer pronunciation;
    private Integer intonation;




    // static factory method
    public static RecordingResponse from(Recording recording, S3Uploader s3Uploader, String key) {
        return new RecordingResponse(
                recording.getId(),
                recording.getFeedBack(),
                s3Uploader.generatePresignedUrl(key),
                recording.getScore().getAccuracy(),
                recording.getScore().getPronunciation(),
                recording.getScore().getIntonation()
        );
    }
}