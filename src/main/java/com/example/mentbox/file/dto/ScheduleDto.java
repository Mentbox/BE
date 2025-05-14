package com.example.mentbox.file.dto;

import lombok.Data;

import java.util.List;


public class ScheduleDto {

    private String formattedDate;
    private List<String> titles;

    public ScheduleDto(String formattedDate, List<String> titles) {
        this.formattedDate = formattedDate;
        this.titles = titles;
    }
}
