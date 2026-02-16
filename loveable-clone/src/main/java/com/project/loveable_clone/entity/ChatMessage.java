package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.MessageRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
         @JoinColumn(name="project_id", referencedColumnName = "project_id", nullable = false),
         @JoinColumn(name="user_id", referencedColumnName = "user_id", nullable = false)
    })
    private ChatSession chatSession;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageRole role; //ASSISTANT, USER

    @Column(columnDefinition = "text", nullable = false)
    private String content;

//    private String toolCalls; //JSON Array of Tools called.

    private Integer tokenUsed=0;

    @CreationTimestamp
    private Instant createdAt;
}
