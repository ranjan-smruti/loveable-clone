package com.project.loveable_clone.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserEntity {
    private Long id;
    private String email;
    private String passwordHash;
    private String name;
    private String avatarUrl;

    //Instant can store UTC time.
    private Instant createdAt;
    private Instant updateAt;

    //soft delete, means only just update the date-time, if the date-time is null user is active else deleted.
    //it will delete user but not from the database.
    private Instant deletedAt;
}
