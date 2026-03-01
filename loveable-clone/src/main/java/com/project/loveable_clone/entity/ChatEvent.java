package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.ChatEventType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name="chat_events")
public class ChatEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //One chat event could be a part of one chat message but one chat message can have
    //many chat events.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    ChatMessage chatMessage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    ChatEventType type;

    //sequence order of chat event in which the chats will be loaded in front end.
    //eg. 1. Thinking for 7s
    //2. Edited file . . .
    //3..
    @Column(nullable = false)
    Integer sequenceOrder;

    @Column(columnDefinition = "text")
    String content;

    String filePath; // NULL unless FILE_EDIT

    @Column(columnDefinition = "text")
    String metadata;
}
