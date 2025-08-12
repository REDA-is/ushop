package org.reda.ushop.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "orders") // avoid reserved word collisions
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private double totalPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Unidirectional one-to-many.
     * Will add an order_id column to cart_item and populate it when the order is saved.
     * NOTE: leave CartItem without "order" field (no bi‑directional mapping needed).
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id") // FK lives in cart_item
    private List<OrderItems> items = new ArrayList<>();


    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
