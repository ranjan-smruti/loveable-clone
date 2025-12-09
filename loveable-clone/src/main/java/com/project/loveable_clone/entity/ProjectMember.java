package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.ProjectMemberRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Table(name="project_members")
public class ProjectMember {
    //This table will not have id but help in mapping USER table and PROJECT.
    //JOIN/MAPPING tables.

    @EmbeddedId
    private ProjectMemberId id;

    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @ManyToOne
    @MapsId("userId")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private ProjectMemberRole role;

    private Instant invitedAt;
    private Instant acceptedAt;
}
