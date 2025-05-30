package com.example.mentbox.common.exception;

public class ThereIsNotThatKeywordException extends MentboxServiceException {

    public ThereIsNotThatKeywordException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ThereIsNotThatKeywordException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
