package com.example.mentbox.recording.entity;

import com.example.mentbox.file.entity.File;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 예시: 어떤 파일(File)에 소속된 녹음인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;


    @OneToOne(mappedBy = "recording", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AudioFile voiceFile;

    @OneToOne(mappedBy = "recording", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Score score;


    @Column(columnDefinition = "TEXT")
    private String feedBack;



}
