package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.MessageRole;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ChatMessage {
    private Long id;
    private ChatSession chatSession;
    private String content;
    private MessageRole role;
    private String toolCalls; //JSON Array of Tools called.
    private Integer tokenUsed;
    private Instant createdAt;
}
