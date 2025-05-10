package com.example.mentbox.file.dto;

import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.entity.Material;
import com.example.mentbox.file.utility.DurationToStringSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FileResponse {

    private Long id;
    private String title;
    private LocalDate targetDate;
    private List<MaterialResponse> materials;

    public FileResponse(Long id, String title, LocalDate targetDate, List<MaterialResponse> materials) {
        this.id = id;
        this.title = title;
        this.targetDate = targetDate;
        this.materials = materials;
    }

    public static FileResponse from(File e) {
        return new FileResponse(
                e.getId(),
                e.getTitle(),
                e.getTargetDate(),
                e.getMaterials().stream()
                        .map(MaterialResponse::from)
                        .collect(Collectors.toList())
        );
    }

    @Data
    public static class MaterialResponse {
        private List<String> keywords;
        private String title;
        private String content;

        /**
         * 연습 시간 (분:초)
         */
        @JsonSerialize(using = DurationToStringSerializer.class)
        private Duration time;

        public MaterialResponse(List<String> keywords,
                                String title,
                                String content,
                                Duration time) {
            this.keywords = keywords;
            this.title = title;
            this.content = content;
            this.time = time;
        }

        public static MaterialResponse from(Material e) {
            return new MaterialResponse(
                    e.getKeywords(),
                    e.getTitle(),
                    e.getContent(),
                    e.getTime()
            );
        }
    }
}