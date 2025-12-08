package com.project.loveable_clone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false)
    private String email;

    private String passwordHash;

    //@Column(nullable=false)
    private String name;

    private String avatarUrl;

    //Instant can store UTC time.
    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updateAt;

    //soft delete, means only just update the date-time, if the date-time is null user is active else deleted.
    //it will delete user but not from the database.
    private Instant deletedAt;
}
