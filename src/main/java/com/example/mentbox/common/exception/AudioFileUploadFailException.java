package com.example.mentbox.common.exception;

public class AudioFileUploadFailException extends MentboxServiceException {

    public AudioFileUploadFailException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AudioFileUploadFailException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
