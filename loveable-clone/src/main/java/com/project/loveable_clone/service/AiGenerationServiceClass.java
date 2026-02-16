package com.project.loveable_clone.service;

import com.project.loveable_clone.service.interfaces.AiGenerationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class AiGenerationServiceClass implements AiGenerationService {
    @Override
    public Flux<String> streamResponse(String message, Long projectId){
        return null;
    }
}
