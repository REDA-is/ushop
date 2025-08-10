package org.reda.ushop.web;

import org.reda.ushop.entities.CartItem;
import org.reda.ushop.services.CartItemService;
import org.reda.ushop.services.JwtService;
import org.reda.ushop.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;


    @PostMapping("/add")
    public CartItem addToCart(@RequestHeader("Authorization") String authHeader,@RequestBody CartItem item) {

        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtils.extractUsername(token);
        item.setUsername(username);
        return cartItemService.addToCart(item);
    }

    @GetMapping
    public List<CartItem> getAllItems() {
        return cartItemService.getAllCartItems();
    }

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCartItemsAndTotal(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String username = JwtUtils.extractUsername(token);

        return ResponseEntity.ok(cartItemService.getCartItemsAndTotal(username));
    }

    @DeleteMapping("/{id}")
    public void removeItem(@PathVariable Long id) {
        cartItemService.removeCartItem(id);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartItemService.clearCart();
    }
}
