package org.reda.ushop.web;

import org.reda.ushop.entities.AppProduct;
import org.reda.ushop.services.AppProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")

public class AppProductController {

    private final AppProductService productService;

    public AppProductController(AppProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<AppProduct> getAllProducts() {
        return productService.getAllProducts();
    }


    @GetMapping("/name/{productname}")
    public List<AppProduct> getProductsByName(@PathVariable("productname") String productName) {
        return productService.getProductsByName(productName);}

    @GetMapping("/category/{category}")
    public List<AppProduct>getProductsByCategory(@PathVariable String category) {
        return productService.getProductsByCategory(category);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<AppProduct>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public AppProduct createProduct(@RequestBody AppProduct product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public AppProduct updateProduct(@PathVariable Long id, @RequestBody AppProduct product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
