package com.distributed_project_companion.intelligence_service.service;



import com.distributed_project_companion.intelligence_service.chat.ChatResponse;

import java.util.List;

public interface ChatService {

    List<ChatResponse> getProjectChatHistory(Long projectId);
}
