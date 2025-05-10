package com.example.mentbox.recording.entity;

import com.example.mentbox.file.entity.File;
import jakarta.persistence.*;

@Entity
public class Recording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;
}
