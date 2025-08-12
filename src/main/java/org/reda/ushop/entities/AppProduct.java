package org.reda.ushop.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_product")
public class AppProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String description;
    private double price;
    private String category;
    private String imageUrl;
    private boolean available;

    @ElementCollection
    private List<Integer> availableQuantities;

    // ===== Constructors =====


    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public List<Integer> getAvailableQuantities() {
        return availableQuantities;
    }

    public void setAvailableQuantities(List<Integer> availableQuantities) {
        this.availableQuantities = availableQuantities;
    }
}
