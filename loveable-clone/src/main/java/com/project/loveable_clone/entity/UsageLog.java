package com.project.loveable_clone.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class UsageLog {
    private Long id;
    private UserEntity user;
    private Project project;
    private String action;
    private Integer tokensUsed;
    private Integer durationMs;
    private String metaData; //JSON of {model_used, prompt_used}
    private Instant createdAt;
}
