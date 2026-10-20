package com.example.demo.services.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.config.ExceptionCustom;
import com.example.demo.dto.ProductRequest;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.ProductRepo;
import com.example.demo.services.ProductServices;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductServices {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepo.findById(id)
            .orElseThrow(() -> new ExceptionCustom("Product not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Product create(ProductRequest request) {
        if (productRepo.existsBySlug(request.slug().trim())) {
            throw new ExceptionCustom("Product slug already exists", HttpStatus.CONFLICT);
        }

        Product product = new Product();
        apply(product, request);
        Product saved = productRepo.saveAndFlush(product);
        entityManager.refresh(saved);
        return saved;
    }

    @Override
    public Product update(Long id, ProductRequest request) {
        Product product = getById(id);

        if (productRepo.existsBySlugAndIdNot(request.slug().trim(), id)) {
            throw new ExceptionCustom("Product slug already exists", HttpStatus.CONFLICT);
        }

        apply(product, request);
        productRepo.flush();
        entityManager.refresh(product);
        return product;
    }

    @Override
    public void delete(Long id) {
        productRepo.delete(getById(id));
    }

    private void apply(Product product, ProductRequest request) {
        Category category = categoryRepo.findById(request.categoryId())
            .orElseThrow(() -> new ExceptionCustom("Category not found", HttpStatus.NOT_FOUND));

        product.setName(request.name().trim());
        product.setSlug(request.slug().trim());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStatus(request.status());
        product.setCategory(category);
    }
}


