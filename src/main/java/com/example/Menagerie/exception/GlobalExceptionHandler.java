package com.example.Menagerie.exception;

import com.example.Menagerie.response.ApiResponse;
import com.example.Menagerie.utils.ApiMessages;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Validation error: {}", ex.getMessage());

        return new ResponseEntity<>(ApiResponse.builder()
                .msgCode(ApiMessages.VALIDATION_FAILED.getCode())
                .message(ex.getMessage())
                .results(null)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.builder()
                .msgCode(ApiMessages.VALIDATION_FAILED.getCode())
                .message(String.join(", ", errors))
                .results(null)
                .build(), HttpStatus.BAD_REQUEST);
    }

    // General Exception handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);

        return new ResponseEntity<>(ApiResponse.builder()
                .msgCode(ApiMessages.ERROR_OCCURRED.getCode())
                .message("An unexpected error occurred: " + ex.getMessage())
                .results(null)
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

