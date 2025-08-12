package org.reda.ushop.web;

import org.reda.ushop.entities.Order;
import org.reda.ushop.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders") // plural is conventional
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 1) Checkout ALL items in the current user's cart
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkoutAll(Authentication auth) { //  no manual JWT parsing
        String username = auth.getName();
        Order created = orderService.createOrderFromCart(username);
        return ResponseEntity.ok(created);
    }

    // 2) Checkout SELECTED items (send cartIds)
    @PostMapping
    public ResponseEntity<Order> checkoutSelected(
            Authentication auth,
            @RequestBody CartSelection body
    ) {
        String username = auth.getName();
        Order created = orderService.saveOrder(username, body.cartIds());
        return ResponseEntity.ok(created);
    }

    // 3) My orders (current user)
    @GetMapping("/me")
    public List<Order> myOrders(Authentication auth) {
        return orderService.getOrdersForUser(auth.getName());
    }

    // 4) All orders (keep only if ADMIN‑protected elsewhere)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();        // safe version
        return ResponseEntity.noContent().build();
    }

    // (optional) delete one order
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    // ---- simple request body for selected checkout
    public record CartSelection(List<Long> cartIds) {}
}
