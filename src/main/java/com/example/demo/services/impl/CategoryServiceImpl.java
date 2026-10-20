package com.example.demo.services.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.config.ExceptionCustom;
import com.example.demo.dto.CategoryRequest;
import com.example.demo.model.Category;
import com.example.demo.repositories.CategoryRepo;
import com.example.demo.repositories.ProductRepo;
import com.example.demo.services.CategoryServices;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryServices {
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Category getById(Long id) {
        return categoryRepo.findById(id)
            .orElseThrow(() -> new ExceptionCustom("Category not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public Category create(CategoryRequest request) {
        if (categoryRepo.existsByName(request.name().trim()) || categoryRepo.existsBySlug(request.slug().trim())) {
            throw new ExceptionCustom("Category name or slug already exists", HttpStatus.CONFLICT);
        }

        Category category = new Category();
        apply(category, request);
        Category saved = categoryRepo.saveAndFlush(category);
        entityManager.refresh(saved);
        return saved;
    }

    @Override
    public Category update(Long id, CategoryRequest request) {
        Category category = getById(id);

        if (categoryRepo.existsByNameAndIdNot(request.name().trim(), id)
                || categoryRepo.existsBySlugAndIdNot(request.slug().trim(), id)) {
            throw new ExceptionCustom("Category name or slug already exists", HttpStatus.CONFLICT);
        }

        apply(category, request);
        categoryRepo.flush();
        entityManager.refresh(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        Category category = getById(id);
        if (productRepo.existsByCategory_Id(id)) {
            throw new ExceptionCustom(
                "Category still has products; move or delete those products first",
                HttpStatus.CONFLICT
            );
        }
        categoryRepo.delete(category);
    }

    private void apply(Category category, CategoryRequest request) {
        category.setName(request.name().trim());
        category.setSlug(request.slug().trim());
        category.setDescription(request.description());
    }
}


