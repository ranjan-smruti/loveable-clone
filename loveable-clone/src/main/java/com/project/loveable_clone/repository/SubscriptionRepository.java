package com.project.loveable_clone.repository;

import com.project.loveable_clone.entity.Subscription;
import com.project.loveable_clone.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByUserIdAndStatusIn(Long userId, Set<SubscriptionStatus> statusSet);
    boolean existsByStripeSubscriptionId(String subscriptionId);
    Optional<Subscription> findByStripeSubscriptionId(String gatewaySubscriptionId);
}
