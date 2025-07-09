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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryRepository;
    private final CategoryReadDtoMapper categoryReadDtoMapper;
    private final CategoryCreateEditMapper categoryCreateEditMapper;

    @Override
    public Page<CategoryReadDto> findAll(Pageable pageable) {
        var categories = categoryRepository.findAll(pageable);
        return categories.map(categoryReadDtoMapper::mapFrom);
    }

    @Override
    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id).map(categoryReadDtoMapper::mapFrom);
    }

    @Transactional
    @Override
    public CategoryReadDto save(CategoryCreateEditDto categoryCreateEditDto) {
        var savedCategory = categoryRepository.save(categoryCreateEditMapper.mapFrom(categoryCreateEditDto));
        return categoryReadDtoMapper.mapFrom(savedCategory);
    }

    @Transactional
    @Override
    public CategoryReadDto update(Long id, CategoryCreateEditDto categoryCreateEditDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));

        categoryCreateEditMapper.mapTo(categoryCreateEditDto, category);
        categoryRepository.save(category);
        return categoryReadDtoMapper.mapFrom(category);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + id));
        categoryRepository.deleteById(id);
    }
}
