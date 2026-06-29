package com.distributed_project_companion.intelligence_service.service;

public interface UsageService {
    void recordTokenUsage(Long userId, int actualTokens);
    void checkDailyTokensUsage();
}
