package com.distributed_project_companion.workspace_service.service;

import com.distributed_project_companion.workspace_service.dto.project.DeployResponse;
import org.jspecify.annotations.Nullable;

public interface DeploymentService {
    @Nullable DeployResponse deploy(Long projectId);
}
