package com.project.loveable_clone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String stripePriceId;

    //How many projects it can support, in free plan there maybe 2 in pro it may 5
    private Integer maxProjects;

    //LLM token, user should not be allowed to use tokens more than that.
    private Integer maxTokensPerDay;

    //When user will preview it will actually run in K8s cluster.
    private Integer maxPreview;

    //Unlimited access to LLM, ignore maxTokensPerDay if True
    private Boolean unlimitedAi;

    //admins only to make plan active or inactive.
    private Boolean active;

    private String feature; //JSON of ARRAY
}
