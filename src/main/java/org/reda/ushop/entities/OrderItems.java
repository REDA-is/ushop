package org.reda.ushop.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "order_items")
public class OrderItems {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long productId;
    private String productName;
    private String imageUrl;

    private double unitPrice;
    private int quantity;
    private double lineTotal;
}
