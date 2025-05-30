package com.example.mentbox.member.dto;
import lombok.Data;

import java.util.List;

@Data
public class MemberResponse {

    private String name;
    private List<String> interests;
    private String profileUrl;


}
