package com.distributed_project_companion.common_lib.event;

public record FileStoreRequestEvent(
        Long projectId,
        String sagaId,
        String filePath,
        String content,
        Long userId
) {}