package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.ProjectMemberRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ProjectMember {
    //This table will not have id but help in mapping USER table and PROJECT.
    //JOIN/MAPPING tables.

    private ProjectMemberId id;

    private Project project;
    private UserEntity user;

    private ProjectMemberRole role;

    private Instant invitedAt;
    private Instant acceptedAt;
}
