package com.project.loveable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import static com.project.loveable_clone.enums.ProjectPermission.*;

@Getter
@RequiredArgsConstructor
public enum ProjectMemberRole {
    //Each enum constant calls a constructor at class-load time.
    //Each role is associated with a set of permissions (ProjectPermission).
    EDITOR(VIEW,EDIT,VIEW_MEMBERS), //Varargs-based initialization
    VIEWER(Set.of(VIEW, VIEW_MEMBERS)), //Set-based initialization (VIEWER, OWNER)
    OWNER(Set.of(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS));

    ProjectMemberRole(ProjectPermission... permissions) {
        this.permissions = Set.of(permissions);
    }

    private final Set<ProjectPermission> permissions;
}
