package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.project.FileContentResponse;
import com.project.loveable_clone.dto.project.FileNode;

import java.util.List;

public interface ProjectFileService {
    List<FileNode> getFileTree(Long projectId);

    FileContentResponse getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
