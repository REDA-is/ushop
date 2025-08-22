package org.reda.ushop.repo;

import org.reda.ushop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order>findByUsernameOrderByCreatedAtDesc(String username);
    Optional<Order> findByIdAndUsername(Long id, String username);

}
