// src/main/java/org/reda/ushop/services/PaymentService.java
package org.reda.ushop.services;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.reda.ushop.entities.Order;
import org.reda.ushop.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {
    @Value("${app.frontend-url}") private String frontendUrl;
    private final OrderRepository orderRepo;
    public PaymentService(OrderRepository orderRepo){ this.orderRepo = orderRepo; }

    private long toMinor(double v){ return Math.round(v * 100); }

    public Session createCheckoutSessionForOrder(Long orderId, String username, String currency) throws Exception {
        Order order = orderRepo.findByIdAndUsername(orderId, username)
                .orElseThrow(() -> new IllegalArgumentException("Order not found or not yours"));
        if (Boolean.TRUE.equals(order.getPaid())) throw new IllegalStateException("Order already paid");

        long amountMinor = toMinor(order.getTotalPrice());
        String cur = (currency == null || currency.isBlank() ? "eur" : currency).toLowerCase();

        var lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(cur).setUnitAmount(amountMinor)
                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName("Order #" + order.getId()).build())
                        .build())
                .build();

        var params = new SessionCreateParams.Builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setClientReferenceId(String.valueOf(order.getId()))
                .setSuccessUrl(frontendUrl + "/order/" + order.getId()
                        + "/payment-success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendUrl + "/order/" + order.getId())
                .addLineItem(lineItem)
                .putMetadata("orderId", String.valueOf(order.getId()))
                .putMetadata("username", username)
                .build();

        return Session.create(params);
    }

    public Session retrieveSession(String id) throws Exception { return Session.retrieve(id); }
}

