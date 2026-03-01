package com.project.loveable_clone.repository;

import com.project.loveable_clone.entity.ChatSession;
import com.project.loveable_clone.entity.ChatSessionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, ChatSessionId> {
}
