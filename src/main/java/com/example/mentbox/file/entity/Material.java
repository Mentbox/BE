package com.example.mentbox.file.entity;

import com.example.mentbox.common.entity.BaseTimeEntity;
import com.example.mentbox.file.utility.DurationToLongConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Material extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 키워드 리스트 (최대 10개, 각 15자 이내)
     */
    @ElementCollection
    @CollectionTable(
            name = "material_keywords",
            joinColumns = @JoinColumn(name = "material_id")
    )
    @Size(max = 10)
    @Column(name = "keyword", length = 15)
    private List<String> keywords = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String content;

    @Column(nullable = false)
    @Convert(converter = DurationToLongConverter.class)
    @Builder.Default
    private Duration time = Duration.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false)
    private File file;


    public void setFile(File file) {
        this.file = file;
    }



}
