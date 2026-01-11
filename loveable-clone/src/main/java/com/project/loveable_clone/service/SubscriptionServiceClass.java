package com.project.loveable_clone.service;

import com.project.loveable_clone.advice.exceptions.ResourceNotFoundException;
import com.project.loveable_clone.dto.subscription.CheckoutRequest;
import com.project.loveable_clone.dto.subscription.CheckoutResponse;
import com.project.loveable_clone.dto.subscription.PortalResponse;
import com.project.loveable_clone.dto.subscription.SubscriptionResponse;
import com.project.loveable_clone.entity.Plan;
import com.project.loveable_clone.entity.Subscription;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.enums.SubscriptionStatus;
import com.project.loveable_clone.mappers.SubscriptionMapper;
import com.project.loveable_clone.repository.PlanRepository;
import com.project.loveable_clone.repository.SubscriptionRepository;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.security.AuthUtil;
import com.project.loveable_clone.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceClass implements SubscriptionService {
    private final AuthUtil authUtil;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionResponse getCurrentSubscription() {
        Long userId = authUtil.getCurrentUserId();
        var currentSubscription = subscriptionRepository.findByUserIdAndStatusIn(userId, Set.of(
                SubscriptionStatus.ACTIVE, SubscriptionStatus.PAST_DUE,SubscriptionStatus.TRIALING
        )).orElse(
                new Subscription()
        );
        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {
        boolean exists = subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
        if(exists){return;}

        UserEntity user = getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription = Subscription.builder()
                .user(user)
                .plan(plan)
                .stripeSubscriptionId(subscriptionId)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);
    }

    @Override
    public void updateSubscription(String subscriptionId, SubscriptionStatus subscriptionStatus, Long planId, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd) {

    }

    @Override
    public void cancelSubscription(String subscriptionId) {

    }

    @Override
    public void renewSubscriptionPeriod(String subscriptionId, Instant periodStart, Instant periodEnd) {
        Subscription subscription = getSubscription(subscriptionId);

        Instant newStart = periodStart != null ? periodStart:subscription.getCurrentPeriodEnd();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if(subscription.getStatus() == SubscriptionStatus.PAST_DUE || subscription.getStatus() == SubscriptionStatus.INCOMPLETE){
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }
        subscriptionRepository.save(subscription);
    }

    @Override
    public void markSubscriptionPastDue(String subscriptionId) {

    }

    ///  Utility methods

    private UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));

    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(() ->
                new ResourceNotFoundException("Subscription", gatewaySubscriptionId));
    }
}
