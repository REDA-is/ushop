package org.reda.ushop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;




@Entity
public class CartItem {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private AppProduct product;

    private Integer selectedQuantity;
    private Double totalPrice;

    private String username;

    public CartItem() {}

    public CartItem(AppProduct product, Integer selectedQuantity, Double totalPrice) {
        this.product = product;
        this.selectedQuantity = selectedQuantity;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public AppProduct getProduct() {
        return product;
    }

    public Integer getSelectedQuantity() {
        return selectedQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(AppProduct product) {
        this.product = product;
    }

    public void setSelectedQuantity(Integer selectedQuantity) {
        this.selectedQuantity = selectedQuantity;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
