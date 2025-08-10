package org.reda.ushop.services;

import org.reda.ushop.entities.AppProduct;

import java.util.List;
import java.util.Optional;

public interface AppProductService {
    List<AppProduct> getAllProducts();
    List<AppProduct> getProductsByName(String productName);
    List<AppProduct> getProductsByCategory(String category);
    AppProduct createProduct(AppProduct product);
    AppProduct updateProduct(Long id, AppProduct product);
    void deleteProduct(Long id);
}

