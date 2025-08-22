package org.reda.ushop.services;

import org.reda.ushop.entities.AppProduct;
import org.reda.ushop.entities.CartItem;
import org.reda.ushop.repo.AppProductRepository;
import org.reda.ushop.repo.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartItemServiceImpl implements CartItemService {

    private CartItemRepository cartItemRepository;
    private AppProductRepository appProductRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, AppProductRepository appProductRepository) {
        this.cartItemRepository = cartItemRepository;
        this.appProductRepository = appProductRepository;
    }

    @Override
    public CartItem addToCart(CartItem item) {
        Long productId = item.getProduct().getId();


        AppProduct product = appProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec id: " + productId));

        if (!product.getAvailableQuantities().contains(item.getSelectedQuantity())) {
            throw new IllegalArgumentException("Quantité non disponible pour ce produit.");
        }
        item.setProduct(product);

        double total = (product.getPrice() / 100.0) * item.getSelectedQuantity(); // prix pour 100g
        item.setTotalPrice(total);

        return cartItemRepository.save(item);
    }

    @Override
    public List<CartItem> getAllCartItems() {

        return cartItemRepository.findAll();
    }
    @Override
    public List<CartItem> getCartItemsByUsername(String username) {
        return cartItemRepository.findByUsername(username);
    }
    @Override
    public Map<String, Object> getCartItemsAndTotal(String username) {
        List<CartItem> items = cartItemRepository.findByUsername(username);
        double total = items.stream().mapToDouble(CartItem::getTotalPrice).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("items", items);
        response.put("totalPrice", total);

        return response;
    }



    @Override
    public void removeCartItem(Long id) {
        cartItemRepository.deleteById(id);

    }

    @Override
    public void clearCart() {
        cartItemRepository.deleteAll();

    }
}
