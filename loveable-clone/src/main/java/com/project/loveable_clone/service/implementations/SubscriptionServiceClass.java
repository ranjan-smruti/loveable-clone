package com.project.loveable_clone.service.implementations;

import com.project.loveable_clone.dto.subscription.CheckoutRequest;
import com.project.loveable_clone.dto.subscription.CheckoutResponse;
import com.project.loveable_clone.dto.subscription.PortalResponse;
import com.project.loveable_clone.dto.subscription.SubscriptionResponse;
import com.project.loveable_clone.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceClass implements SubscriptionService {
    @Override
    public SubscriptionResponse getCurrentSubscription(Long userId) {
        return null;
    }

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
