package com.example.mentbox.common.exception;

public class ThereIsNotThatMemberException extends MentboxServiceException{
    public ThereIsNotThatMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ThereIsNotThatMemberException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
