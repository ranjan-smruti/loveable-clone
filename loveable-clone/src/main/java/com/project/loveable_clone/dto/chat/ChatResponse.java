package com.project.loveable_clone.dto.chat;

import com.project.loveable_clone.entity.ChatEvent;
import com.project.loveable_clone.entity.ChatSession;
import com.project.loveable_clone.enums.MessageRole;

import java.time.Instant;
import java.util.List;

public record ChatResponse(
        Long id,
        ChatSession chatSession,
        MessageRole role,
        List<ChatEvent> events,
        String content,
        Integer tokensUsed,
        Instant createdAt
) {
}
