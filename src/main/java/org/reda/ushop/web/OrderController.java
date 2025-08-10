package org.reda.ushop.web;

import org.reda.ushop.entities.Order;
import org.reda.ushop.services.OrderService;
import org.reda.ushop.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Crée une commande à partir du panier de l'utilisateur courant
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtils.extractUsername(token);
        Order created = orderService.createOrderFromCart(username);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}

