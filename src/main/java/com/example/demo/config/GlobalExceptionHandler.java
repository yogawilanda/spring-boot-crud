package com.example.demo.config;

import java.time.Instant;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path) {
    }

    @ExceptionHandler(ExceptionCustom.class)
    public ResponseEntity<ErrorResponse> handleCustomException(
            ExceptionCustom exception,
            HttpServletRequest request) {

        HttpStatus status = exception.getStatus();

        ErrorResponse body = new ErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
}