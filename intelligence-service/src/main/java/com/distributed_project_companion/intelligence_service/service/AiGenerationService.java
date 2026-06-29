package com.distributed_project_companion.intelligence_service.service;

import com.distributed_project_companion.intelligence_service.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {
    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
