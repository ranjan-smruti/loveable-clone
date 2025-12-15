package com.project.loveable_clone.controller;

import com.project.loveable_clone.dto.project.ProjectRequest;
import com.project.loveable_clone.dto.project.ProjectResponse;
import com.project.loveable_clone.dto.project.ProjectSummaryResponse;
import com.project.loveable_clone.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    //TODO: Should have another endpoint to get all he user projects(own+invited)

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProject(){
        //TODO: update later with real Spring Security
        Long userId =  2L;
        return ResponseEntity.ok(projectService.getUserProjects(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id)
    {
        Long userId = 2L;
        return ResponseEntity.ok(projectService.getUserProjectById(id, userId));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request)
    {
        Long userId = 3L;
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request, userId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id,
                                                         @RequestBody @Valid ProjectRequest request)
    {
        Long userId = 2L;
        return ResponseEntity.ok(projectService.updateProject(id,request,userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id)
    {
        Long userId = 1L;
        projectService.softDelete(id, userId);
        return ResponseEntity.noContent().build();
    }
}
