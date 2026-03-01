package com.project.loveable_clone.dto.chat;

import com.project.loveable_clone.enums.ChatEventType;

public record ChatEventResponse(
        Long id,
        ChatEventType type,
        Integer sequencerOder,
        String content,
        String filePath,
        String metadata
) {
}
