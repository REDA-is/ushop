package org.reda.ushop.repo;

import org.reda.ushop.entities.AppProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppProductRepository extends JpaRepository<AppProduct, Long> {
     List<AppProduct> findByProductNameContainingIgnoreCase(String productName);
     List<AppProduct> findByCategoryContainingIgnoreCase(String category);
     List<AppProduct> findById(long id);


}

