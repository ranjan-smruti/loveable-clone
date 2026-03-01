package com.project.loveable_clone.mappers;

import com.project.loveable_clone.dto.chat.ChatResponse;
import com.project.loveable_clone.entity.ChatMessage;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel="spring")
public interface ChatMapper {
    List<ChatResponse> fromListOfChatMessage(List<ChatMessage> chatMessageList);
}
