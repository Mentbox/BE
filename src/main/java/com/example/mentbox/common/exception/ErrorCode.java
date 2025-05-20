package com.example.mentbox.common.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    NoFilesForMemberException("해당 사용자가 소유한 파일이 없습니다."),
    FILE_NOT_FOUND("해당 파일은 존재하지 않습니다"),
    AudioFileUploadFail("오디오 파일 업로드에 실패하였습니다."),
    AccessDenied("해당 파일에 대한 권한이 없습니다."),
    RECORDING_NOT_FOUND("해당 연습 기록을 찾을 수 없습니다."),
    ;


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
