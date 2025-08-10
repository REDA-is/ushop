package org.reda.ushop.services;

import lombok.RequiredArgsConstructor;
import org.reda.ushop.entities.CartItem;
import org.reda.ushop.entities.Order;
import org.reda.ushop.entities.OrderItem;
import org.reda.ushop.repo.CartItemRepository;
import org.reda.ushop.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Order saveOrder(Order order) {

        List<CartItem> fullItems = order.getItems().stream()
                .map(item -> cartItemRepository.findById(item.getId())
                        .orElseThrow(() -> new RuntimeException("CartItem non trouvé: " + item.getId())))
                .toList();

        order.setItems(fullItems);

        // Calcul du total
        double total = fullItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order createOrderFromCart(String username) {
        List<CartItem> cartItems = cartItemRepository.findByUsername(username);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("cart is empty !");
        }

        double total = cartItems.stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();

        Order order = new Order();
        order.setUsername(username);
        order.setItems(cartItems);
        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);

        // vide le panier après commande
        cartItemRepository.deleteByUsername(username);

        return savedOrder;
    }
}









