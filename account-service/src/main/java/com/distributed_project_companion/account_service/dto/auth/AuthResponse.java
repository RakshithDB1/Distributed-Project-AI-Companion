package com.distributed_project_companion.account_service.dto.auth;

public record AuthResponse(
        String token,
        UserProfileResponse user
) {

}
