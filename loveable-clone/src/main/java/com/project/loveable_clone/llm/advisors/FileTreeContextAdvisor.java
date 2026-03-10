package com.project.loveable_clone.llm.advisors;

import com.project.loveable_clone.dto.project.FileNode;
import com.project.loveable_clone.service.interfaces.ProjectFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileTreeContextAdvisor implements StreamAdvisor {
    private final ProjectFileService projectFileService;

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest chatClientRequest, StreamAdvisorChain streamAdvisorChain) {
        Map<String, Object> context = chatClientRequest.context();
        Long projectId = Long.parseLong(context.getOrDefault("projectId",0).toString());
        ChatClientRequest augmentedChatClientRequest = augmentRequestsWithFileTree(chatClientRequest, projectId);
        return streamAdvisorChain.nextStream(augmentedChatClientRequest);
    }

    //take the user prompt and append the file content at the end of the prompt.
    private ChatClientRequest augmentRequestsWithFileTree(ChatClientRequest chatClientRequest, Long projectId){
        List<Message> incomingMessages = chatClientRequest.prompt().getInstructions();
        Message systemMessage = incomingMessages.stream()
                .filter(m -> m.getMessageType() == MessageType.SYSTEM)
                .findFirst()
                .orElse(null);

        List<Message> userMessage = incomingMessages.stream()
                .filter(m -> m.getMessageType() != MessageType.SYSTEM)
                .toList();

        List<Message> allMessages = new ArrayList<>();

        //Add original system message
        if(systemMessage != null){
            allMessages.add(systemMessage);
        }

        //Get all the file path for this project id from db.
        List<FileNode> fileTree = projectFileService.getFileTree(projectId).files();

        String fileTreeContext = "\n\n ---- FILE_TREE ---- \n" + fileTree.toString();
        allMessages.add(new SystemMessage(fileTreeContext));
        allMessages.addAll(userMessage);

        return chatClientRequest
                .mutate()
                .prompt(new Prompt(allMessages, chatClientRequest.prompt().getOptions()))
                .build();
    }

    @Override
    public String getName() {
        return "FileTreeContextAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

//System Prompt + File Tree + User message