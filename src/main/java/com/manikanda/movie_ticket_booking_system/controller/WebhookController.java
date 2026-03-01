package com.manikanda.movie_ticket_booking_system.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.manikanda.movie_ticket_booking_system.services.PaymentService.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
@Slf4j
public class WebhookController {

    private final PaymentService paymentService;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping(value = "/webhook", consumes = "application/json")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        // Step 1: Verify webhook signature
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe webhook signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        log.info("Received Stripe event: {}", event.getType());

        // Step 2: Handle checkout.session.completed event
        if ("checkout.session.completed".equals(event.getType())) {
            handleCheckoutSessionCompleted(payload);
        }

        return ResponseEntity.ok("Event processed");
    }

    private void handleCheckoutSessionCompleted(String payload) {
        try {
            // Parse payload JSON directly
            JsonObject root = JsonParser.parseString(payload).getAsJsonObject();
            JsonObject data = root.getAsJsonObject("data");
            JsonObject object = data.getAsJsonObject("object");
            
            String sessionId = object.get("id").getAsString();
            String paymentStatus = object.get("payment_status").getAsString();
            
            log.info("✅ Session ID: {}", sessionId);
            log.info("✅ Payment Status: {}", paymentStatus);
            
            if ("paid".equals(paymentStatus)) {
                log.info("Processing payment for session: {}", sessionId);
                paymentService.updatePaymentAndBooking(sessionId);
            } else {
                log.warn("⚠️ Payment not paid. Status: {}", paymentStatus);
            }

        } catch (Exception e) {
            log.error("❌ Error processing checkout session: {}", e.getMessage(), e);
        }
    }

}