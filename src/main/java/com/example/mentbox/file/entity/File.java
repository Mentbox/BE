package com.example.mentbox.file.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.file.dto.FileRequest;
import com.example.mentbox.recording.entity.Recording;
import jakarta.persistence.*;
import lombok.*;

import com.example.mentbox.member.entity.Member;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 모든 SELECT 시 deleted = false 조건이 자동으로 붙음
@Where(clause = "deleted = false")
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    String title;

    @Column
    LocalDate targetDate;



    @OneToMany(
            mappedBy = "file",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Material> materials = new ArrayList<>();



    @OneToMany(
            mappedBy = "file",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Recording> recordings = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void addMaterial(Material material) {
        material.setFile(this);
        materials.add(material);

    }

    public void addRecording(Recording recording) {
        this.recordings.add(recording);
    }

    public void removeMaterial(Material material) {
        materials.remove(material);
        material.setFile(null);
    }
    public void update(FileRequest dto) {
        this.title = dto.getTitle();
        this.targetDate = dto.getTargetDate();
        this.materials.clear();
        for (FileRequest.MaterialDto material : dto.getMaterials()) {
            this.addMaterial(material.toEntity());
        }
        this.recordings.clear();
        List<Recording> recordings = dto.getRecordings();
        if (recordings != null) {
            for (Recording recording : recordings) {
                this.recordings.add(recording);
                recording.setFile(this);
            }
        }
    }

    @Override
    public void markDeleted() {
        super.markDeleted();

        for (Material material : materials) {
            material.markDeleted();
        }

        for (Recording recording : recordings) {
            recording.markDeleted();
        }
    }
}
