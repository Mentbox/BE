package com.example.mentbox.file.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.recording.entity.Recording;
import jakarta.persistence.*;
import lombok.*;

import com.example.mentbox.member.entity.Member;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
        materials.add(material);
        material.setFile(this);
    }

    public void removeMaterial(Material material) {
        materials.remove(material);
        material.setFile(null);
    }





}
