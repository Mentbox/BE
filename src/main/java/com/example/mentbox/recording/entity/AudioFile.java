package com.example.mentbox.recording.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.member.entity.Member;
import jakarta.persistence.*;

@Entity
public class AudioFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // presigned URL을 만들기 위한 S3 object key
    @Column(nullable = false, unique = true)
    private String objectKey;

    // 사용자가 업로드한 원본 파일 이름
    @Column(nullable = false)
    private String originalFileName;

    // 파일 크기 (byte 기준)
    @Column(nullable = false)
    private Long fileSize;

    // MIME 타입 (ex: audio/mpeg)
    @Column(nullable = false)
    private String contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private Member uploadedBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recording_id", nullable = false, unique = true) // FK이자 유일해야 함
    private Recording recording;

}
