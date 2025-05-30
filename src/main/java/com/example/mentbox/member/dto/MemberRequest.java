package com.example.mentbox.member.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberRequest {

    private String name;
    private List<String> interests;
}
