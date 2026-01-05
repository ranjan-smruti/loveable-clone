package com.project.loveable_clone.security;

import com.project.loveable_clone.enums.ProjectPermission;
import com.project.loveable_clone.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("security")
@RequiredArgsConstructor
public class SecurityExpression {
    private final ProjectMemberRepository projectMemberRepository;
    private final AuthUtil authUtil;

    private boolean hasPermission(Long projectId, ProjectPermission permission) {
        Long userId = authUtil.getCurrentUserId();

        return projectMemberRepository.findRoleByProjectIdAndUserId(projectId, userId)
                .map(role -> role.getPermissions().contains(permission))
                .orElse(false);
    }

    public boolean hasPermissionToView(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW);
    }

    public boolean hasPermissionToEdit(Long projectId){
        return hasPermission(projectId, ProjectPermission.EDIT);
    }

    public boolean hasPermissionToDelete(Long projectId){
        return hasPermission(projectId, ProjectPermission.DELETE);
    }

    public boolean hasPermissionToViewMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.VIEW_MEMBERS);
    }

    public boolean hasPermissionToManageMembers(Long projectId){
        return hasPermission(projectId, ProjectPermission.MANAGE_MEMBERS);
    }
}
