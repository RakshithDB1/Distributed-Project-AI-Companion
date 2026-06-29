package com.distributed_project_companion.workspace_service.dto.member;


import com.distributed_project_companion.common_lib.enums.ProjectRole;

import java.time.Instant;

public record MemberResponse(
        Long userId,
        String username,
        String name,
        ProjectRole projectRole,
        Instant invitedAt
) {
}
