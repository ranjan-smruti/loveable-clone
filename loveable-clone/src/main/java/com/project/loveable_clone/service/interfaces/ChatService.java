package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.chat.ChatResponse;

import java.util.List;

public interface ChatService {
    List<ChatResponse> getProjectChatHistory(Long projectId);
}
