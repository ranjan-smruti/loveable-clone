package com.project.loveable_clone.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProjectPermission {
    //This enum defines fine-grained permissions and associates each permission with a string identifier.
    //Each enum constant represents one permission in the system.
    //"project:view" is a constructor argument. This value is assigned to the enum field: private final String permission;

    //So when the enum loads:
    //VIEW("project:view") -> new ProjectPermission("project:view")
    //EDIT("project:edit") -> new ProjectPermission("project:edit")

    //API / JWT claims
    //{
    //  "permissions": ["project:view", "project:edit"]
    //}

    VIEW("project:view"),
    EDIT("project:edit"),
    DELETE("project:delete"),

    MANAGE_MEMBERS("project_member:manage"),
    VIEW_MEMBERS("project_member:view");

    private final String permission;
}
