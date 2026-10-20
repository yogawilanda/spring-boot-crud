package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, Long id);
    boolean existsByCategory_Id(Long categoryId);
}
