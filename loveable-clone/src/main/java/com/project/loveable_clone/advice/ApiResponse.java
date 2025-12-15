package com.project.loveable_clone.advice;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ApiResponse<T>(
        HttpStatus status,
        String msg,
        Long ts,
        @JsonInclude(JsonInclude.Include.NON_NULL) T data,
        @JsonInclude(JsonInclude.Include.NON_NULL)List<ApiFieldError> errors
        )
{
    //success response
    public ApiResponse(T data) {
        this(HttpStatus.OK, "Success", Instant.now().toEpochMilli(), data, null);
    }

    //success response with custom message
    public ApiResponse(HttpStatus status, String message, T data) {
        this(status, message, Instant.now().toEpochMilli(), data, null);
    }

    //error response
    public ApiResponse(HttpStatus status, String message, List<ApiFieldError> errors) {
        this(status, message, Instant.now().toEpochMilli(), null, errors);
    }
}

record ApiFieldError(String field, String message){}
