package com.project.loveable_clone.service.interfaces;

import com.project.loveable_clone.dto.subscription.CheckoutRequest;
import com.project.loveable_clone.dto.subscription.CheckoutResponse;
import com.project.loveable_clone.dto.subscription.PortalResponse;
import com.project.loveable_clone.dto.subscription.SubscriptionResponse;
import com.project.loveable_clone.enums.SubscriptionStatus;

import java.time.Instant;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription();
    void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId);

    void updateSubscription(String gatewaySubscriptionId,
                            SubscriptionStatus subscriptionStatus,
                            Long planId,
                            Instant periodStart,
                            Instant periodEnd,
                            Boolean cancelAtPeriodEnd);

    void cancelSubscription(String gatewaySubscriptionId);
    void renewSubscriptionPeriod(String subscriptionId, Instant periodStart, Instant periodEnd);
    void markSubscriptionPastDue(String subscriptionId);
    boolean canCreateNewProject();
}
