package com.project.loveable_clone.advice.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final String resourceName;
    private final String resourceId;
}
