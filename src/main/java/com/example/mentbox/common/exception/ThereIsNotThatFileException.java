package com.example.mentbox.common.exception;

public class ThereIsNotThatFileException extends MentboxServiceException{
    public ThereIsNotThatFileException(ErrorCode errorCode) {
        super(errorCode);
    }
}
