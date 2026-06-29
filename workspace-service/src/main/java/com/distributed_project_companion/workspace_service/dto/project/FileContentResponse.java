package com.distributed_project_companion.workspace_service.dto.project;

public record FileContentResponse(
        String path,
        String content
) {
}
