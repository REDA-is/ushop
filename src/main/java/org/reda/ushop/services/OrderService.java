package org.reda.ushop.services;

import org.reda.ushop.entities.Order;

import java.util.List;

public interface OrderService {
    Order saveOrder(Order order);
    List<Order> getAllOrders();
    Order createOrderFromCart(String username);
}
