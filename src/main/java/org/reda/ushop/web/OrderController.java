package org.reda.ushop.web;

import org.reda.ushop.entities.Order;
import org.reda.ushop.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/checkout")
    public ResponseEntity<Order> checkoutAll(Authentication auth) {
        String username = auth.getName();
        Order created = orderService.createOrderFromCart(username);
        return ResponseEntity.ok(created);
    }


    @PostMapping
    public ResponseEntity<Order> checkoutSelected(
            Authentication auth,
            @RequestBody CartSelection body
    ) {
        String username = auth.getName();
        Order created = orderService.saveOrder(username, body.cartIds());
        return ResponseEntity.ok(created);
    }


    @GetMapping("/me")
    public List<Order> myOrders(Authentication auth) {
        return orderService.getOrdersForUser(auth.getName());
    }


    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.noContent().build();
    }


    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }


    public record CartSelection(List<Long> cartIds) {}
}
