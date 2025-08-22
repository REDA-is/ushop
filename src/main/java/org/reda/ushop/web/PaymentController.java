package org.reda.ushop.web;

import lombok.RequiredArgsConstructor;
import org.reda.ushop.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController @RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/create-checkout-session")
    public ResponseEntity<?> payOrder(@PathVariable Long orderId,
                                      @RequestParam(defaultValue = "eur") String currency) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return ResponseEntity.status(401).build();
        try {
            var s = paymentService.createCheckoutSessionForOrder(orderId, auth.getName(), currency);
            return ResponseEntity.ok(Map.of("url", s.getUrl(), "id", s.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/session/{id}")
    public ResponseEntity<?> getSession(@PathVariable String id) throws Exception {
        var s = paymentService.retrieveSession(id);
        return ResponseEntity.ok(Map.of(
                "id", s.getId(),
                "paymentStatus", s.getPaymentStatus(),
                "clientReferenceId", s.getClientReferenceId(),
                "metadata", s.getMetadata(),
                "paymentIntent", s.getPaymentIntent(),
                "amountTotal", s.getAmountTotal(),
                "currency", s.getCurrency()
        ));
    }
}
