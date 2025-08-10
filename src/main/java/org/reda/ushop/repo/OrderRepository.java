package org.reda.ushop.repo;

import org.reda.ushop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUsernameOrderByCreatedAtDesc(String username);
}
