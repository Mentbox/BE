package com.example.mentbox.common.exception.handler;

import com.example.mentbox.common.exception.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MentboxServiceException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFound(MentboxServiceException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode.name(), errorCode.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(AudioFileUploadFailException.class)
    public ResponseEntity<ErrorResponse> handleAudioFileFail(AudioFileUploadFailException ex) {
        log.error("오디오 파일 S3 업로드 실패", ex);

        ErrorResponse body = new ErrorResponse(
                ex.getErrorCode().name(),
                ex.getErrorCode().getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)  // 상태 코드 설정 (예: 502)
                .body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleFileAccess(AccessDeniedException ex) {

        ErrorResponse body = new ErrorResponse(
                ex.getErrorCode().name(),
                ex.getErrorCode().getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)  // 상태 코드 설정 (예: 502)
                .body(body);
    }

    @ExceptionHandler(NotSupportingSocialTypeException.class)
    public ResponseEntity<String> handleNotSupportingSocialTypeException(NotSupportingSocialTypeException ex) {
        return ResponseEntity
                .badRequest()
                .body(ex.getMessage());
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ErrorResponse> handleExpiredToken(ExpiredTokenException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    @ExceptionHandler(UnAuthorizedTokenException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorized(UnAuthorizedTokenException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    @ExceptionHandler(ThereIsNotThatKeywordException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportKeyword(ThereIsNotThatKeywordException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorCode.name(), errorCode.getMessage()));
    }

    @ExceptionHandler(AudioFileEmptyException.class)
    public ResponseEntity<ErrorResponse> handleAudioFileEmpty(AudioFileEmptyException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorCode.name(),  errorCode.getMessage()));
    }


    @RequiredArgsConstructor
    @Getter
    public static class ErrorResponse {
        private final String code;
        private final String message;


    }
}
