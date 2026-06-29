package com.distributed_project_companion.common_lib.event;

import lombok.Builder;

@Builder
public record FileStoreResponseEvent(
        String sagaId,
        boolean success,
        String errorMessage,
        Long projectId
) {}