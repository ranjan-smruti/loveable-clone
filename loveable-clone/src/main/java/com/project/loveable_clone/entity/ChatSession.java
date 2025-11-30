package com.project.loveable_clone.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ChatSession {
    private Project project;
    private UserEntity user;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

}
