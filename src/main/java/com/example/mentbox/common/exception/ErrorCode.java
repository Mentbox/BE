package com.example.mentbox.common.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    NoFilesForMemberException("해당 사용자가 소유한 파일이 없습니다.");
    ;


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
