package com.example.demo.services;

import java.util.List;
import com.example.demo.dto.CategoryRequest;
import com.example.demo.model.Category;

public interface CategoryServices {
    List<Category> getAll();
    Category getById(Long id);
    Category create(CategoryRequest request);
    Category update(Long id, CategoryRequest request);
    void delete(Long id);
}
