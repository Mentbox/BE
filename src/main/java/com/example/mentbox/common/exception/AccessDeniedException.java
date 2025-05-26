package com.example.mentbox.common.exception;

public class AccessDeniedException extends MentboxServiceException{

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
