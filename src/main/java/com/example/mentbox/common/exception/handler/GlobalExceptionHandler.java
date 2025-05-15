package com.example.mentbox.common.exception.handler;

import com.example.mentbox.common.exception.ErrorCode;
import com.example.mentbox.common.exception.MentboxServiceException;
import com.example.mentbox.common.exception.ThereIsNotThatFileException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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

    @RequiredArgsConstructor
    @Getter
    public static class ErrorResponse {
        private final String code;
        private final String message;


    }
}
