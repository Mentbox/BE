package com.example.mentbox.recording.dto;

import com.example.mentbox.recording.entity.Recording;
import com.example.mentbox.recording.entity.Score;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordingRequest {

    @Size(max = 1000, message = "피드백은 1000자 이하로 입력해주세요.")
    private String feedBack;

    @Valid
    @NotNull
    private ScoreRequest score;

    public Recording toEntity() {

        return Recording.builder()
                .feedBack(this.feedBack)
                .score(this.score.toEntity())
                .build();
    }

    @Data
    public static class ScoreRequest {

        @Min(value = 0, message = "정확도는 0 이상이어야 합니다.")
        @Max(value = 100, message = "정확도는 100 이하여야 합니다.")
        @NotNull
        private Integer accuracy;

        @Min(value = 0, message = "발음 점수는 0 이상이어야 합니다.")
        @Max(value = 100, message = "발음 점수는 100 이하여야 합니다.")
        @NotNull
        private Integer pronunciation;

        @Min(value = 0, message = "억양 점수는 0 이상이어야 합니다.")
        @Max(value = 100, message = "억양 점수는 100 이하여야 합니다.")
        @NotNull
        private Integer intonation;

        public Score toEntity() {
            return Score.builder()
                    .accuracy(this.accuracy)
                    .pronunciation(this.pronunciation)
                    .intonation(this.intonation)
                    .build();
        }
    }
}
