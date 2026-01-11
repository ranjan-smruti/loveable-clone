package com.project.loveable_clone.service;

import com.project.loveable_clone.advice.exceptions.ResourceNotFoundException;
import com.project.loveable_clone.advice.exceptions.UsernameNotFoundException;
import com.project.loveable_clone.dto.subscription.CheckoutRequest;
import com.project.loveable_clone.dto.subscription.CheckoutResponse;
import com.project.loveable_clone.dto.subscription.PortalResponse;
import com.project.loveable_clone.entity.Plan;
import com.project.loveable_clone.entity.UserEntity;
import com.project.loveable_clone.enums.SubscriptionStatus;
import com.project.loveable_clone.repository.PlanRepository;
import com.project.loveable_clone.repository.UserRepository;
import com.project.loveable_clone.security.AuthUtil;
import com.project.loveable_clone.service.interfaces.PaymentProcessor;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripePaymentProcessor implements PaymentProcessor {
    private final AuthUtil authUtil;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SubscriptionServiceClass subscriptionService;

    @Value("${client.url}")
    private String frontendUrl;

    @Override
    public CheckoutResponse createCheckoutSessionUrl(CheckoutRequest request) {
        Plan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Plan",request.planId().toString()));

        Long userId = authUtil.getCurrentUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User",userId.toString()));

        var params = SessionCreateParams.builder()
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setPrice(plan.getStripePriceId()).setQuantity(1L).build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSubscriptionData(
                        new SessionCreateParams.SubscriptionData.Builder()
                                .setBillingMode(SessionCreateParams.SubscriptionData.BillingMode.builder()
                                        .setType(SessionCreateParams.SubscriptionData.BillingMode.Type.FLEXIBLE)
                                        .build())
                                .build()
                )
                .setSuccessUrl(frontendUrl + "/success.html?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/cancel.html")
                //.setExpiresAt(Instant.now().plusSeconds(60*10).getEpochSecond())
                .putMetadata("user_id", userId.toString()) //metadata will be sent back by the webhook.
                .putMetadata("plan_id", plan.getId().toString());

        try {
            String stripeCustomerId = user.getStripeCustomerId();
            if(stripeCustomerId == null || stripeCustomerId.isEmpty()) {
                params.setCustomerEmail(user.getUsername());
            } else {
                params.setCustomer(stripeCustomerId); // stripe customer Id
            }
            Session session = Session.create(params.build()); // making api call to the Strip Backend
            return new CheckoutResponse(session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PortalResponse openCustomerPortal() {
        return null;
    }

    @Override
    public void handleWebhookEvent(String eventType, StripeObject stripeObject, Map<String, String> metadata) {
        log.debug("Received Webhook event: {}", eventType);
        switch(eventType){
            case "checkout.session.completed":
                handleCheckoutSessionCompleted((Session) stripeObject, metadata);
                break;
            case "customer.subscription.updated":
                handleCustomerSubscriptionUpdated((Subscription) stripeObject);
                break;
            case "customer.subscription.deleted":
                handleCustomerSubscriptionDeleted((Subscription) stripeObject);
                break;
            case "invoice.paid":
                handleInvoicePaid((Invoice) stripeObject);
                break;
            case "invoice.payment_failed":
                handleInvoicePaymentFailed((Invoice) stripeObject);
                break;
            default:
                log.debug("Unknown event type: {}", eventType);
                break;
        }

    }

    //Helper methods
    private void handleCheckoutSessionCompleted(Session session, Map<String, String> metadata) {
        if(session == null){
            log.error("Session object was null");
            return;
        }

        Long userId = Long.parseLong(metadata.get("user_id"));
        Long planId = Long.parseLong(metadata.get("plan_id"));

        String subscriptionId = session.getSubscription();
        String customerId = session.getCustomer();

        UserEntity user = getUser(userId);
        if(user.getStripeCustomerId() == null){
            user.setStripeCustomerId(customerId);
            userRepository.save(user);
        }

        subscriptionService.activateSubscription(userId, planId, subscriptionId, customerId);
    }

    private void handleCustomerSubscriptionUpdated(Subscription subscription) {
        if(subscription == null){
            log.error("Subscription object was null inside handleCustomerSubscriptionUpdated");
            return;
        }

        SubscriptionStatus status = mapStripeStatusToEnum(subscription.getStatus());
        if(status == null){
            log.warn("Unknown status '{}' for subscription '{}'", subscription.getStatus(), subscription.getId());
            return;
        }

        SubscriptionItem item = subscription.getItems().getData().getFirst();
        Instant periodStart = toInstant(item.getCurrentPeriodStart());
        Instant periodEnd = toInstant(item.getCurrentPeriodEnd());

        Long planId = resolvePlanId(item.getPrice());

        subscriptionService.updateSubscription(subscription.getId(), status, planId, periodStart, periodEnd, subscription.getCancelAtPeriodEnd());
    }

    private void handleCustomerSubscriptionDeleted(Subscription subscription) {
        if (subscription == null) {
            log.error("subscription object was null inside handleCustomerSubscriptionDeleted");
            return;
        }
        subscriptionService.cancelSubscription(subscription.getId());
    }

    private void handleInvoicePaid(Invoice invoice) {
        String subId = extractSubscriptionId(invoice);
        if(subId == null) return;

        try {
            Subscription subscription = Subscription.retrieve(subId); //sdk calling the Stripe server
            var item = subscription.getItems().getData().getFirst();

            Instant periodStart = toInstant(item.getCurrentPeriodStart());
            Instant periodEnd = toInstant(item.getCurrentPeriodEnd());

            subscriptionService.renewSubscriptionPeriod(
                    subId,
                    periodStart,
                    periodEnd
            );

        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleInvoicePaymentFailed(Invoice invoice) {
        String subId = extractSubscriptionId(invoice);
        if(subId == null) return;

        subscriptionService.markSubscriptionPastDue(subId);
    }

    //Utility Methods
    private UserEntity getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("user", userId.toString()));
    }

    private SubscriptionStatus mapStripeStatusToEnum(String stripeStatus) {
        return switch (stripeStatus) {
            case "active" -> SubscriptionStatus.ACTIVE;
            case "trialing" -> SubscriptionStatus.TRIALING;
            case "past_due", "unpaid", "paused", "incomplete_expired" -> SubscriptionStatus.PAST_DUE;
            case "canceled" -> SubscriptionStatus.CANCELED;
            case "incomplete" -> SubscriptionStatus.INCOMPLETE;
            default -> {
                log.warn("Unmapped Stripe status: {}", stripeStatus);
                yield null;
            }
        };
    }

    private Instant toInstant(Long epoch) {
        return epoch != null ? Instant.ofEpochSecond(epoch) : null;
    }

    private Long resolvePlanId(Price price) {
        if (price == null || price.getId() == null) return null;
        return planRepository.findByStripePriceId(price.getId())
                .map(Plan::getId)
                .orElse(null);
    }

    private String extractSubscriptionId(Invoice invoice) {
        var parent = invoice.getParent();
        if (parent == null) return null;

        var subDetails = parent.getSubscriptionDetails();
        if (subDetails == null) return null;

        return subDetails.getSubscription();
    }

}
