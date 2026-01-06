package com.project.loveable_clone.controller;

import com.project.loveable_clone.dto.subscription.*;
import com.project.loveable_clone.service.interfaces.PaymentProcessor;
import com.project.loveable_clone.service.interfaces.PlanService;
import com.project.loveable_clone.service.interfaces.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
@RequiredArgsConstructor
public class BillingController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;
    private final PaymentProcessor paymentProcessor;

    @GetMapping("/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans()
    {
        return ResponseEntity.ok(planService.getAllActivePlans());
    }

    @GetMapping("/profile/subscription")
    public ResponseEntity<SubscriptionResponse> getMySubscription()
    {
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription());
    }

    @PostMapping("/payment/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(@RequestBody CheckoutRequest request)
    {
        return ResponseEntity.ok(paymentProcessor.createCheckoutSessionUrl(request));
    }

    @PostMapping("/stripe/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal()
    {
        return ResponseEntity.ok(paymentProcessor.openCustomerPortal());
    }
}
