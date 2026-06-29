package com.distributed_project_companion.account_service.dto.auth;

public record UserProfileResponse(
        Long id,
        String username,
        String name
) {
}
