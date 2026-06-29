package com.distributed_project_companion.account_service.dto.subscription;

public record UsageTodayResponse(
        Integer tokensUsed,
        Integer tokensLimit,
        Integer previewsRunning,
        Integer previewsLimit
) {
}
