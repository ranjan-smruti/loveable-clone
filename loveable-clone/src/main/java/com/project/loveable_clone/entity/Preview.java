package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.PreviewStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Preview {
    //This will help to store the details to communicate with the pod running application for review.
    private Long id;

    private Project project;

    private String namespace;
    private String podName;
    private String previewUrl;

    private PreviewStatus status;

    private Instant startedAt;
    private Instant terminatedAt;
    private Instant createdAt;


}
