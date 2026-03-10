package com.project.loveable_clone.controller;

import com.project.loveable_clone.dto.project.FileContentResponse;
import com.project.loveable_clone.dto.project.FileNode;
import com.project.loveable_clone.dto.project.FileTreeResponse;
import com.project.loveable_clone.service.interfaces.ProjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/files")
@RequiredArgsConstructor
public class FileController {
    private final ProjectFileService projectFileService;

    @GetMapping
    public ResponseEntity<FileTreeResponse> getFileTree(@PathVariable Long projectId)
    {
        return ResponseEntity.ok(projectFileService.getFileTree(projectId));
    }

    @GetMapping("/content")
    public ResponseEntity<FileContentResponse> getFile(@PathVariable Long projectId, @RequestParam String path)
    {
        return ResponseEntity.ok(projectFileService.getFileContent(projectId, path));
    }
}
