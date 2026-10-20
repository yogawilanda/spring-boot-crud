package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    boolean existsBySlug(String slug);
    boolean existsByNameAndIdNot(String name, Long id);
    boolean existsBySlugAndIdNot(String slug, Long id);
}
