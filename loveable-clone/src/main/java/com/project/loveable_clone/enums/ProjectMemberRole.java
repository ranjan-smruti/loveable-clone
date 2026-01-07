package com.project.loveable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import static com.project.loveable_clone.enums.ProjectPermission.*;

@Getter
@RequiredArgsConstructor
public enum ProjectMemberRole {
    //Each enum constant calls a constructor at class-load time.
    //Each role is associated with a set of permissions (ProjectPermission).
    EDITOR(VIEW,EDIT,VIEW_MEMBERS), //Varargs-based initialization
    VIEWER(VIEW, VIEW_MEMBERS), //Set-based initialization (VIEWER, OWNER)
    OWNER(VIEW, EDIT, DELETE, MANAGE_MEMBERS, VIEW_MEMBERS);

    ProjectMemberRole(ProjectPermission... permissions) {
        this.permissions = EnumSet.copyOf(Arrays.asList(permissions));
    }

    private final Set<ProjectPermission> permissions;
}
