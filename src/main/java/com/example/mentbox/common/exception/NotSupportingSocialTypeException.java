package com.example.mentbox.common.exception;

public class NotSupportingSocialTypeException extends MentboxServiceException{
    public NotSupportingSocialTypeException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotSupportingSocialTypeException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
