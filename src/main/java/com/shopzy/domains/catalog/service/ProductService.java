package com.shopzy.domains.catalog.service;

import com.shopzy.domains.catalog.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> searchProducts(String keyword);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
