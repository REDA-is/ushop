package org.reda.ushop.services;


import org.reda.ushop.entities.CartItem;
import java.util.List;
import java.util.Map;

public interface CartItemService {
    CartItem addToCart(CartItem item);
    List<CartItem> getAllCartItems();

    List<CartItem> getCartItemsByUsername(String username);
    Map<String, Object> getCartItemsAndTotal(String username);
    void removeCartItem(Long id);
    void clearCart();
}

