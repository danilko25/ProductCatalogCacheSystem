package com.danilko.product_catalog_cache_system.service;

import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;

import java.util.List;

public interface CategoryService {
    List<CategoryReadDto> findAll();
    CategoryReadDto findById(Long id);
    CategoryReadDto save(CategoryCreateEditDto categoryCreateEditDto);
    CategoryReadDto update(Long id, CategoryCreateEditDto categoryCreateEditDto);
    void delete(Long id);
}
