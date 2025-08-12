package org.reda.ushop.services;

import org.reda.ushop.entities.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(String username, List<Long> cartIds);
    List<Order> getAllOrders();
    Order createOrderFromCart(String username);
    List<Order> getOrdersForUser(String username);
    void deleteAllOrders();
    void deleteOrderById(Long id);
}
