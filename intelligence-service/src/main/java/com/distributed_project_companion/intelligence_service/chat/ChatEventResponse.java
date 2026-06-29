package com.distributed_project_companion.intelligence_service.chat;


import com.distributed_project_companion.common_lib.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequenceOrder,
        String content,
        String filePath,
        String metadata
) {
}
