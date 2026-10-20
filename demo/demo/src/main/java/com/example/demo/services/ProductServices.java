package com.example.demo.services;

import java.util.List;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Product;

public interface ProductServices {
    List<Product> getAll();
    Product getById(Long id);
    Product create(ProductRequest request);
    Product update(Long id, ProductRequest request);
    void delete(Long id);
}
