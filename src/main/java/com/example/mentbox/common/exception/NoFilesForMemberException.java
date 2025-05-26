package com.example.mentbox.common.exception;

public class NoFilesForMemberException extends MentboxServiceException{

    public NoFilesForMemberException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoFilesForMemberException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
