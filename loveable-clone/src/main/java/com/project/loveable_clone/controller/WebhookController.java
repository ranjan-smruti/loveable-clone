package com.project.loveable_clone.controller;

import com.project.loveable_clone.advice.ApiResponse;
import com.project.loveable_clone.config.PaymentConfig;
import com.project.loveable_clone.service.interfaces.PaymentProcessor;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/webhooks")
public class WebhookController {
    private final PaymentConfig paymentConfig;
    private final PaymentProcessor paymentProcessor;

    @PostMapping("/payment")
    public ResponseEntity<ApiResponse<String>> handlePaymentWebhook(@RequestBody String payload,
                                                              @RequestHeader("Stripe-Signature")  String signHeader) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, signHeader, paymentConfig.getStripeWebhookSecret());
        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (deserializer.getObject().isPresent()) { // happy case
            stripeObject = deserializer.getObject().get();
        } else {
            // Fallback: Deserialize from raw JSON
            try {
                stripeObject = deserializer.deserializeUnsafe();
                if (stripeObject == null) {
                    log.warn("Failed to deserialize webhook object for event: {}", event.getType());
                    return ResponseEntity.ok().build();
                }
            } catch (Exception e) {
                log.error("Unsafe deserialization failed for event {}: {}", event.getType(), e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Deserialization failed"));
            }
        }

        // Now extract metadata only if it's a Checkout Session
        Map<String, String> metadata = new HashMap<>();
        if (stripeObject instanceof Session session) {
            metadata = session.getMetadata();
        }

        // Pass to your processor
        paymentProcessor.handleWebhookEvent(event.getType(), stripeObject, metadata);
        return ResponseEntity.ok().build();
    }
}
