package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.deploy.DeployResponse;

public interface DeploymentService {
    DeployResponse deploy(Long projectId);
    void deletePodOnProjectDelete(Long projectId);
}
