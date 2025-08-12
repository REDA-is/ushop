package org.reda.ushop.services;

import org.reda.ushop.entities.CartItem;
import org.reda.ushop.entities.Order;
import org.reda.ushop.entities.OrderItems;
import org.reda.ushop.repo.CartItemRepository;
import org.reda.ushop.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Order saveOrder(String username, List<Long> cartIds) {
        if (cartIds == null || cartIds.isEmpty()) {
            throw new RuntimeException("Order items are empty.");
        }

        // 1) Load the selected cart items
        List<CartItem> fullItems = cartItemRepository.findAllById(cartIds);
        if (fullItems.size() != cartIds.size()) {
            throw new RuntimeException("Some CartItem IDs are invalid.");
        }


        List<OrderItems> orderItems = fullItems.stream().map(ci ->
                OrderItems.builder()
                        .productId(ci.getProduct().getId())
                        .productName(ci.getProduct().getProductName())
                        .imageUrl(ci.getProduct().getImageUrl())
                        .unitPrice(ci.getProduct().getPrice())
                        .quantity(ci.getSelectedQuantity())
                        .lineTotal(ci.getTotalPrice())
                        .build()
        ).toList();


        double total = orderItems.stream().mapToDouble(OrderItems::getLineTotal).sum();

        Order order = Order.builder()
                .username(username)
                .totalPrice(total)
                .items(new java.util.ArrayList<>(orderItems))
                .build();

        Order saved = orderRepository.save(order);

        // 4) Clear only the selected cart rows
        cartItemRepository.deleteAllById(cartIds);

        return saved;
    }

    @Override
    @Transactional
    public Order createOrderFromCart(String username) {
        // 1) Load ALL cart items for user
        List<CartItem> cartItems = cartItemRepository.findByUsername(username);
        if (cartItems.isEmpty()) throw new RuntimeException("cart is empty !");


        List<OrderItems> orderItems = cartItems.stream().map(ci ->
                OrderItems.builder()
                        .productId(ci.getProduct().getId())
                        .productName(ci.getProduct().getProductName())
                        .imageUrl(ci.getProduct().getImageUrl())
                        .unitPrice(ci.getProduct().getPrice())
                        .quantity(ci.getSelectedQuantity())
                        .lineTotal(ci.getTotalPrice())
                        .build()
        ).toList();


        double total = orderItems.stream().mapToDouble(OrderItems::getLineTotal).sum();

        Order order = Order.builder()
                .username(username)
                .totalPrice(total)
                .items(new java.util.ArrayList<>(orderItems))
                .build();

        Order saved = orderRepository.save(order);

        // 4) Clear the whole cart
        cartItemRepository.deleteByUsername(username);

        return saved;
    }

    @Override
    public List<Order> getOrdersForUser(String username) {
        return orderRepository.findByUsernameOrderByCreatedAtDesc(username);
    }
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    @Transactional
    public void deleteAllOrders() {
        orderRepository.deleteAll();           // cascades per-entity, safe
    }

    @Override
    @Transactional
    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);        // cascades to its items
    }

}