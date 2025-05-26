package com.example.mentbox.common.exception;

public class ExpiredTokenException extends MentboxServiceException {

    public ExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ExpiredTokenException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
