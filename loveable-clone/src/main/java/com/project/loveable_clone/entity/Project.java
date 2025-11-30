package com.project.loveable_clone.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class Project {
    private Long id;

    private String name;

    private UserEntity owner;

    private Boolean isPublic = false;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
