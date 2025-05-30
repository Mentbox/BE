package com.example.mentbox.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class SignupDetailRequest {

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 15, message = "이름은 15자 이내여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "이름은 한글 또는 영문만 입력 가능합니다.")
    private String name;

    @Size(min = 1, max = 3, message = "관심사는 최소 1개 최대 3개까지 선택되어야 합니다.")
    private List<String> keywords;
}
