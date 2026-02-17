package com.project.loveable_clone.mappers;

import com.project.loveable_clone.dto.project.FileNode;
import com.project.loveable_clone.entity.ProjectFile;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {
    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
