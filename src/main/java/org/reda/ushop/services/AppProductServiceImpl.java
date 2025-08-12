package org.reda.ushop.services;

import org.reda.ushop.entities.AppProduct;
import org.reda.ushop.repo.AppProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional

public class AppProductServiceImpl implements AppProductService {
    private AppProductRepository appProductRepository;

    public AppProductServiceImpl(AppProductRepository appProductRepository) {
        this.appProductRepository = appProductRepository;
    }

    @Override
    public List<AppProduct> getAllProducts() {
        return appProductRepository.findAll();
    }

    @Override
    public List<AppProduct> getProductsByName(String productName) {
        return appProductRepository.findByProductNameContainingIgnoreCase(productName);
    }


    @Override
    public List<AppProduct> getProductsByCategory(String category) {
        return appProductRepository.findByCategoryContainingIgnoreCase(category);
    }


    @Override
    public AppProduct createProduct(AppProduct product) {

        return appProductRepository.save(product);
    }

    @Override
    public AppProduct updateProduct(Long id, AppProduct product) {
        product.setId(id);
        return appProductRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        appProductRepository.deleteById(id);

    }
    @Override
    public Optional<AppProduct> getProductById(Long id) {
        return appProductRepository.findById(id);
    }
}
