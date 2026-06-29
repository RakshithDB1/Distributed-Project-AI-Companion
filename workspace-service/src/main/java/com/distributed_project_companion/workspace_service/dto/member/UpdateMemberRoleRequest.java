package com.distributed_project_companion.workspace_service.dto.member;

import com.distributed_project_companion.common_lib.enums.ProjectRole;
import jakarta.validation.constraints.NotNull;

public record UpdateMemberRoleRequest(
        @NotNull ProjectRole role) {
}
