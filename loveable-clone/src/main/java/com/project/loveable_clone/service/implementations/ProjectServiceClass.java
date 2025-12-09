package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.ExceptionHandler.ResourceNotFoundException;
import com.project.loveable_clone.dto.project.ProjectRequest;
import com.project.loveable_clone.dto.project.ProjectResponse;
import com.project.loveable_clone.dto.project.ProjectSummaryResponse;
import com.project.loveable_clone.entity.Project;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.mappers.ProjectMapper;
import com.project.loveable_clone.repository.ProjectRepository;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceClass implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    @Override
    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        UserEntity owner = userRepository.findById(userId).orElseThrow();

        Project project = Project.builder()
                .name(request.name())
                .owner(owner)
                .isPublic(false)
                .build();

        project = projectRepository.save(project);

        return projectMapper.toProjectResponse(project);
    }

    @Override
    public List<ProjectSummaryResponse> getUserProjects(Long userId) {
//        return projectRepository.findAllAccessibleByUser(userId)
//                .stream()
//                .map(projectMapper::toProjectSummaryResponse)
//                .collect(Collectors.toList());

        List<Project> projects = projectRepository.findAllAccessibleByUser(userId);

        return projectMapper.toListOfProjectSummaryResponse(projects);
    }

    @Override
    public ProjectResponse getUserProjectById(Long projectId, Long userId) {

        //UserEntity user = userRepository.findById(userId).orElseThrow();
        //Check if the user exists or not.

        Project project = getAccessibleProjectById(projectId, userId);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public ProjectResponse updateProject(Long id, ProjectRequest request, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("You are not the owner of this project");
        }

        project.setName(request.name());
        project = projectRepository.save(project);
        return projectMapper.toProjectResponse(project);
    }

    @Override
    public void softDelete(Long id, Long userId) {
        Project project = getAccessibleProjectById(id, userId);
        if(!project.getOwner().getId().equals(userId)){
            throw new RuntimeException("You are not allowed to delete this project");
        }

        project.setDeletedAt(Instant.now());
        projectRepository.save(project);
    }

    //INTERNAL FUNCTIONS
    private Project getAccessibleProjectById(Long projectId, Long userId){
        return projectRepository.findUserProjectsById(projectId,userId).orElseThrow(() -> new ResourceNotFoundException ("Not accessible by this user"));
    }
}
