package com.project.loveable_clone.service;

import com.project.loveable_clone.dto.chat.ChatResponse;
import com.project.loveable_clone.entity.ChatMessage;
import com.project.loveable_clone.entity.ChatSession;
import com.project.loveable_clone.entity.ChatSessionId;
import com.project.loveable_clone.mappers.ChatMapper;
import com.project.loveable_clone.repository.ChatMessageRepository;
import com.project.loveable_clone.repository.ChatSessionRepository;
import com.project.loveable_clone.security.AuthUtil;
import com.project.loveable_clone.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceClass implements ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final AuthUtil authUtil;
    private final ChatMapper chatMapper;

    @Override
    public List<ChatResponse> getProjectChatHistory(Long projectId) {
        Long userId = authUtil.getCurrentUserId();

        ChatSession chatSession = chatSessionRepository.getReferenceById(
                new ChatSessionId(projectId, userId)
        );

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatSession(chatSession);

        return chatMapper.fromListOfChatMessage(chatMessageList);
    }
}
