// src/main/java/.../web/StripeWebhookController.java
package org.reda.ushop.web;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reda.ushop.entities.WebhookEventLog;
import org.reda.ushop.repo.WebhookEventLogRepository;
import org.reda.ushop.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
public class StripeWebhookController {

    @Value("${stripe.webhook-secret}") private String endpointSecret;
    private final OrderService orderService;
    private final WebhookEventLogRepository eventLogRepo;

    @PostMapping("/webhook")
    public ResponseEntity<String> handle(@RequestBody String payload,
                                         @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            log.warn("Webhook: bad signature");
            return ResponseEntity.status(400).body("bad signature");
        }

        if (eventLogRepo.existsById(event.getId())) {
            log.info("Webhook: {} already processed", event.getId());
            return ResponseEntity.ok("already processed");
        }

        try {
            if (!"checkout.session.completed".equals(event.getType())) {
                log.info("Webhook: event {} ignored", event.getType());
                return ResponseEntity.ok("ignored");
            }


            Session s = null;
            var deser = event.getDataObjectDeserializer();
            if (deser.getObject().isPresent() && deser.getObject().get() instanceof Session ss) {
                s = ss;
            } else {
                String id = JsonMapper.builder().build()
                        .readTree(payload).path("data").path("object").path("id").asText(null);
                if (id != null && !id.isBlank()) s = Session.retrieve(id);
            }

            if (s == null) {
                log.error("Webhook: no Session in event {}", event.getId());
                return ResponseEntity.ok("no session");
            }

            log.info("Webhook: session={} status={} payment_status={} metadata={} clientRef={}",
                    s.getId(), s.getStatus(), s.getPaymentStatus(), s.getMetadata(), s.getClientReferenceId());

            if (!"paid".equalsIgnoreCase(s.getPaymentStatus())) {
                log.info("Webhook: session {} not paid ({}), skipping", s.getId(), s.getPaymentStatus());
                return ResponseEntity.ok("not paid");
            }

            String orderIdStr = (s.getMetadata() != null ? s.getMetadata().get("orderId") : null);
            if (orderIdStr == null || orderIdStr.isBlank()) orderIdStr = s.getClientReferenceId();
            if (orderIdStr == null || orderIdStr.isBlank()) {
                log.error("Webhook: missing orderId in metadata and client_reference_id for session {}", s.getId());
                return ResponseEntity.ok("missing orderId");
            }

            log.info("Webhook: marking order {} as PAID (session={}, pi={})",
                    orderIdStr, s.getId(), s.getPaymentIntent());

            orderService.markOrderPaid(
                    Long.valueOf(orderIdStr),
                    s.getId(),
                    s.getPaymentIntent(),
                    s.getCurrency(),
                    s.getAmountTotal()
            );
            eventLogRepo.save(new WebhookEventLog(event.getId()));
            return ResponseEntity.ok("ok");
        } catch (Exception ex) {
            log.error("Webhook: processing error", ex);
            return ResponseEntity.status(500).body("processing error");
        }
    }
}
