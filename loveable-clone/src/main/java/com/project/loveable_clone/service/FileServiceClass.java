package com.project.loveable_clone.service;

import com.project.loveable_clone.dto.project.FileContentResponse;
import com.project.loveable_clone.dto.project.FileNode;
import com.project.loveable_clone.service.interfaces.FileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceClass implements FileService {
    @Override
    public List<FileNode> getFileTree(Long projectId, Long userId) {
        return List.of();
    }

    @Override
    public FileContentResponse getFileContent(Long projectId, String path, Long userId) {
        return null;
    }
}
