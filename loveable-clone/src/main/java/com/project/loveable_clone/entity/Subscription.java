package com.project.loveable_clone.entity;

import com.project.loveable_clone.enums.SubscriptionStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class Subscription {
    private Long id;
    private UserEntity user;
    private Plan plan;

    private String stripeCustomerId;
    private String stripeSubscriptionId;
    private SubscriptionStatus subscriptionStatus;

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;
    private Boolean cancelAtPeriodEnd = false;

    private Instant createdAt;
    private Instant updatedAt;
}
