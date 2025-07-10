package com.danilko.product_catalog_cache_system.service;

import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryReadDto> findAll();
    Optional<CategoryReadDto> findById(Long id);
    CategoryReadDto save(CategoryCreateEditDto categoryCreateEditDto);
    CategoryReadDto update(Long id, CategoryCreateEditDto categoryCreateEditDto);
    void delete(Long id);
}
