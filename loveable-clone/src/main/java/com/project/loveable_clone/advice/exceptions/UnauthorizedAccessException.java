package com.project.loveable_clone.advice.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnauthorizedAccessException extends RuntimeException {
    private final String message;
}
