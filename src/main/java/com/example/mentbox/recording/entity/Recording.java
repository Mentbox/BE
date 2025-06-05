package com.example.mentbox.recording.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.recording.dto.RecordingRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Where(clause = "deleted = false")
public class Recording extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예시: 어떤 파일(File)에 소속된 녹음인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;


    @OneToOne(mappedBy = "recording", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AudioFile audioFile;

    @OneToOne(mappedBy = "recording", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Score score;


    @Column(columnDefinition = "TEXT")
    private String feedBack;

    public void setScore(Score score) {
        this.score = score;
        score.setRecording(this);
    }

    public void setAudioFile(AudioFile audioFile) {
        this.audioFile = audioFile;
        audioFile.setRecording(this);
    }

    public void setFile(File file) {
        this.file = file;
        file.addRecording(this);
    }

    public void update(RecordingRequest request) {

        RecordingRequest.ScoreRequest score = request.getScore();
        this.score.setAccuracy(score.getAccuracy());
        this.score.setPronunciation(score.getPronunciation());
        this.score.setIntonation(score.getIntonation());

        this.feedBack = request.getFeedBack();

    }

    @Override
    public void markDeleted() {
        super.markDeleted();
        audioFile.markDeleted();
        score.markDeleted();
    }
}
