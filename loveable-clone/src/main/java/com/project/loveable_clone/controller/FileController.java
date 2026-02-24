package com.project.loveable_clone.controller;

import com.project.loveable_clone.dto.project.FileContentResponse;
import com.project.loveable_clone.dto.project.FileNode;
import com.project.loveable_clone.service.interfaces.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/files")
@RequiredArgsConstructor
public class FileController {
    private final ProjectFileService projectFileService;

    @GetMapping
    public ResponseEntity<List<FileNode>> getFileTree(@PathVariable Long projectId)
    {
        return ResponseEntity.ok(projectFileService.getFileTree(projectId));
    }

    @GetMapping("/{*path}") // /src/hooks/get-user-hook.jsx file path returned from the project structure
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId, @PathVariable String path)
    {
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }
}
