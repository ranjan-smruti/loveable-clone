package com.project.loveable_clone.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ChatSessionId implements Serializable {
    private Long projectId;
    private Long userId;
}
