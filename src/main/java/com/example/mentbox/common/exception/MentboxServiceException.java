package com.example.mentbox.common.exception;

import org.springframework.core.NestedRuntimeException;


public class MentboxServiceException extends NestedRuntimeException {

    private final ErrorCode errorCode;


    public MentboxServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


    public MentboxServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}
