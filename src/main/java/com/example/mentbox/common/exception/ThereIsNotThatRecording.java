package com.example.mentbox.common.exception;

public class ThereIsNotThatRecording extends MentboxServiceException{
    public ThereIsNotThatRecording(ErrorCode errorCode) {
        super(errorCode);
    }

    public ThereIsNotThatRecording(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
