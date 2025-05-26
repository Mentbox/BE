package com.example.mentbox.common.exception;

public class UnAuthorizedTokenException extends MentboxServiceException {
    public UnAuthorizedTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public UnAuthorizedTokenException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
