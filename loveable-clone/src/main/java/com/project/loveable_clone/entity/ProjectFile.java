package com.project.loveable_clone.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ProjectFile {
    private Long id;

    private Project project;

    //src path
    private String path;

    private String minioObjectKey;

    private Instant createdAt;
    private Instant updatedAt;

    private UserEntity createdBy;
    private UserEntity updatedBy;
}
