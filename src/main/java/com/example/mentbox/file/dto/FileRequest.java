package com.example.mentbox.file.dto;

import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.entity.Material;
import com.example.mentbox.file.utility.StringToDurationDeserializer;
import com.example.mentbox.recording.entity.Recording;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FileRequest {

    @NotBlank
    @Size(max = 20)
    private String title;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate targetDate;

    @Valid
    private List<MaterialDto> materials;

    private List<Recording> recordings;

    public List<Material> materialsDtoToMaterials(List<MaterialDto> materialDtos) {
        List<Material> result = new ArrayList<>();

        for (MaterialDto materialDto : materialDtos) {
            result.add(materialDto.toEntity());
        }

        return result;
    }

    public File toEntity() {
        // 1) Builder 로 File 객체 생성
        File file = File.builder()
                .title(this.title)
                .targetDate(this.targetDate)
                .recordings(this.recordings)
                .build();

        // 2) MaterialDto → Material via builder, helper 메서드로 연관관계 설정
        for (MaterialDto md : this.materials) {
            file.addMaterial(md.toEntity());
        }
        return file;
    }


    @Data
    @NoArgsConstructor
    public static class MaterialDto {
        @Size(max = 10)
        private List<@Size(max = 15) String> keywords;

        @NotBlank
        private String title;

        @Size(max = 1000)
        private String content;

        @NotNull
        @JsonDeserialize(using = StringToDurationDeserializer.class)
        private Duration time;

        public Material toEntity() {
            return Material.builder()
                    .keywords(this.keywords)
                    .title(this.title)
                    .content(this.content)
                    .time(this.time)
                    .build();
        }
    }

}
