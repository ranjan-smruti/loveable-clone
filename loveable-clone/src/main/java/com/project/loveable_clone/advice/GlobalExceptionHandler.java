package com.project.loveable_clone.advice;

import com.project.loveable_clone.advice.exceptions.BadRequestException;
import com.project.loveable_clone.advice.exceptions.ResourceNotFoundException;
import com.project.loveable_clone.advice.exceptions.UnauthorizedAccessException;
import com.project.loveable_clone.advice.exceptions.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(BadRequestException ex)
    {
        ApiResponse<Void> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        ApiResponse<Void> apiError = new ApiResponse<>(HttpStatus.NOT_FOUND, ex.getResourceName() + " with id " + ex.getResourceId() + " not found", null);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUsernameNotFoundException(UsernameNotFoundException ex)
    {
        ApiResponse<Void> apiError = new ApiResponse<>(HttpStatus.NOT_FOUND, ex.getResourceName() + " with " + ex.getResourceId() + " not found", null);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnauthorizedAccessException(UnauthorizedAccessException ex)
    {
        ApiResponse<Void> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleInputValidationError(MethodArgumentNotValidException ex) {

        List<ApiFieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiFieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiResponse<Void> apiError = new ApiResponse<>(HttpStatus.BAD_REQUEST, "Input Validation Failed", errors);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

}
