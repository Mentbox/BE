package com.example.mentbox.recording.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
public class RecordingDetailResponse {

    private String fileName;
    private LocalDate targetDate;
    private String audioUrl;
    private String feedback;
    private int accuracy;
    private int pronunciation;
    private int intonation;
}
