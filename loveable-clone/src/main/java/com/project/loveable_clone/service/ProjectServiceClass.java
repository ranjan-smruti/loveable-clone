package com.project.loveable_clone.service;

import com.project.loveable_clone.advice.exceptions.BadRequestException;
import com.project.loveable_clone.advice.exceptions.ResourceNotFoundException;
import com.project.loveable_clone.advice.exceptions.UnauthorizedAccessException;
import com.project.loveable_clone.dto.project.ProjectRequest;
import com.project.loveable_clone.dto.project.ProjectResponse;
import com.project.loveable_clone.dto.project.ProjectSummaryResponse;
import com.project.loveable_clone.entity.Project;
import com.project.loveable_clone.entity.ProjectMember;
import com.project.loveable_clone.entity.ProjectMemberId;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.enums.ProjectMemberRole;
import com.project.loveable_clone.mappers.ProjectMapper;
import com.project.loveable_clone.repository.ProjectMemberRepository;
import com.project.loveable_clone.repository.ProjectRepository;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.security.AuthUtil;
import com.project.loveable_clone.service.interfaces.ProjectService;
import com.project.loveable_clone.service.interfaces.ProjectTemplateService;
import com.project.loveable_clone.service.interfaces.SubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceClass implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;
    private final ProjectTemplateService projectTemplateService;
    private final KubernetesDeploymentService deploymentService;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {

        if(!subscriptionService.canCreateNewProject()){
            throw new BadRequestException("User cannot create a new project with current plan, Upgrade plan now.");
        }

        Long userId = authUtil.getCurrentUserId();
        UserEntity owner = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User ", userId.toString())
        );

        Project project = Project.builder()
                .name(request.name())
                .isPublic(false)
                .build();

        project = projectRepository.save(project);

        //assign project role
        ProjectMemberId projectmemberId = new ProjectMemberId(project.getId(), owner.getId());
        ProjectMember projectMember = ProjectMember.builder()
                .id(projectmemberId)
                .role(ProjectMemberRole.OWNER)
                .user(owner)
                .acceptedAt(Instant.now())
                .invitedAt(Instant.now())
                .project(project)
                .build();
        projectMemberRepository.save(projectMember);

        projectTemplateService.initializeProjectFromTemplate(project.getId());

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects() {
        Long userId = authUtil.getCurrentUserId();
        var projectsWithRoles = projectRepository.findAllAccessibleByUser(userId);
        return projectsWithRoles.stream()
                .map(p -> projectMapper.toProjectSummaryResponse(p.getProject(), p.getRole()))
                .toList();
    }

    @Override
    @PreAuthorize("@security.hasPermissionToView(#projectId)")
    public ProjectSummaryResponse getUserProjectById(Long projectId) {
        //UserEntity user = userRepository.findById(userId).orElseThrow();
        //Check if the user exists or not.
        Long userId = authUtil.getCurrentUserId();
        var projectWithRole = projectRepository.findAccessibleProjectByIdWithRole(projectId, userId)
                .orElseThrow(() -> new BadRequestException("Project not found."));
        return projectMapper.toProjectSummaryResponse(projectWithRole.getProject(), projectWithRole.getRole());
    }

    @Override
    @PreAuthorize("@security.hasPermissionToEdit(#projectId)")
    public ProjectResponse updateProject(Long projectId, ProjectRequest request) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    @PreAuthorize("@security.hasPermissionToDelete(#projectId)")
    public void softDelete(Long projectId) {
        Long userId = authUtil.getCurrentUserId();
        Project project = getAccessibleProjectById(projectId, userId);

        project.setDeletedAt(Instant.now());
        
        //Delete assigned pod for this project(if any)
        deploymentService.deletePodOnProjectDelete(projectId);

        projectRepository.save(project);
    }

    //INTERNAL FUNCTIONS
    private Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findAccessibleById(projectId,userId).orElseThrow(() -> new UnauthorizedAccessException("Not accessible by this user"));
    }
}
