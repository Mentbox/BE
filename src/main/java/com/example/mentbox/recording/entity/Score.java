package com.example.mentbox.recording.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Where(clause = "deleted = false")
public class Score extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    // 정확도 점수 (0~100)
    @Column(nullable = false)
    private Integer accuracy;

    // 발음 점수
    @Column(nullable = false)
    private Integer pronunciation;

    // 억양 점수
    @Column(nullable = false)
    private Integer intonation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recording_id", nullable = false, unique = true)
    private Recording recording;




}
