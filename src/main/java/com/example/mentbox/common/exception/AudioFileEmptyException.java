package com.example.mentbox.common.exception;

public class AudioFileEmptyException extends MentboxServiceException{

    public AudioFileEmptyException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AudioFileEmptyException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
