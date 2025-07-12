package com.danilko.product_catalog_cache_system.service.impl;

import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.mapper.createEditMapper.CategoryCreateEditMapper;
import com.danilko.product_catalog_cache_system.mapper.readMapper.CategoryReadDtoMapper;
import com.danilko.product_catalog_cache_system.repository.CategoryJpaRepository;
import com.danilko.product_catalog_cache_system.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryRepository;
    private final CategoryReadDtoMapper categoryReadDtoMapper;
    private final CategoryCreateEditMapper categoryCreateEditMapper;

    private static final String CATEGORY_LIST_CACHE = "categoryList";
    private static final String SINGLE_CATEGORY_CACHE = "category";

    @Cacheable(value = CATEGORY_LIST_CACHE)
    @Override
    public List<CategoryReadDto> findAll() {
        var categories = categoryRepository.findAll();
        return categories.stream().map(categoryReadDtoMapper::mapFrom).toList();
    }

    @Cacheable(value = SINGLE_CATEGORY_CACHE, key = "#id")
    @Override
    public CategoryReadDto findById(Long id) {
        var  categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(categoryReadDtoMapper::mapFrom).orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
    }

    @CacheEvict(value = CATEGORY_LIST_CACHE, allEntries = true)
    @Transactional
    @Override
    public CategoryReadDto save(CategoryCreateEditDto categoryCreateEditDto) {
        var savedCategory = categoryRepository.save(categoryCreateEditMapper.mapFrom(categoryCreateEditDto));
        return categoryReadDtoMapper.mapFrom(savedCategory);
    }

    @CacheEvict(value = CATEGORY_LIST_CACHE, allEntries = true)
    @CachePut(value = SINGLE_CATEGORY_CACHE, key = "#id")
    @Transactional
    @Override
    public CategoryReadDto update(Long id, CategoryCreateEditDto categoryCreateEditDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));

        categoryCreateEditMapper.mapTo(categoryCreateEditDto, category);
        categoryRepository.save(category);
        return categoryReadDtoMapper.mapFrom(category);
    }

    @Caching(evict = {
            @CacheEvict(value = SINGLE_CATEGORY_CACHE, key = "#id"),
            @CacheEvict(value = CATEGORY_LIST_CACHE, allEntries = true)
    })
    @Transactional
    @Override
    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        categoryRepository.deleteById(id);
    }
}
