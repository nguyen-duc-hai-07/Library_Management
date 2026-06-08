package com.library.exception;

import com.library.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return ResponseEntity.status(404).body(new ApiResponse<>(null,e.getMessage(), 404));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return ResponseEntity.status(401).body(new ApiResponse<>(null,e.getMessage(), 401));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> BadRequestException(Exception e) {
        log.error("Exception: {}", e.getMessage());
        return ResponseEntity.status(400).body(new ApiResponse<>(null,e.getMessage(), 400));
    }
}