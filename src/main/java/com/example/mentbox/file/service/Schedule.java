package com.example.mentbox.file.service;

import com.example.mentbox.file.dto.ScheduleDto;
import com.example.mentbox.file.entity.File;
import com.example.mentbox.file.repository.FileRepository;
import com.example.mentbox.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class Schedule {

    private final FileRepository fileRepository;

    public List<ScheduleDto> getSchedule(Member member) {

        List<File> files = fileRepository.findByMemberOrderByTargetDateAsc(member);

        Map<LocalDate, List<String>> grouped = new LinkedHashMap<>(); //저장 순서를 보장하기 위해서 HashMap이 아닌 LinkedHashMap 사용
        for (File file : files) {
            LocalDate date = file.getTargetDate();
            String title = file.getTitle();

            if (!grouped.containsKey(date)) {
                grouped.put(date, new ArrayList<>());
            }

            grouped.get(date).add(title);
        }

        List<ScheduleDto> result = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN);

        for (Map.Entry<LocalDate, List<String>> entry : grouped.entrySet()) {
            String formattedDate = entry.getKey().format(formatter);
            List<String> titles = entry.getValue();

            result.add(new ScheduleDto(formattedDate, titles));
        }

        return result;
    }
}
