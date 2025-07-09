package com.danilko.product_catalog_cache_system.service.impl;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.entity.Product;
import com.danilko.product_catalog_cache_system.mapper.createEditMapper.ProductCreateEditMapper;
import com.danilko.product_catalog_cache_system.mapper.readMapper.ProductReadDtoMapper;
import com.danilko.product_catalog_cache_system.repository.CategoryJpaRepository;
import com.danilko.product_catalog_cache_system.repository.ProductJpaRepository;
import com.danilko.product_catalog_cache_system.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productRepository;
    private final CategoryJpaRepository categoryRepository;
    private final ProductReadDtoMapper  productReadDtoMapper;
    private final ProductCreateEditMapper productCreateEditDtoMapper;

    @Override
    public Page<ProductReadDto> findAll(Pageable pageable) {
        var products = productRepository.findAll(pageable);
        return products.map(productReadDtoMapper::mapFrom);
    }

    @Override
    public Page<ProductReadDto> findByCategoryId(Long id, Pageable pageable) {
        var productsByCategory = productRepository.findAllByCategoryId(id, pageable);
        return productsByCategory.map(productReadDtoMapper::mapFrom);
    }

    @Override
    public Optional<ProductReadDto> findById(Long id) {
        return productRepository.findById(id).map(productReadDtoMapper::mapFrom);
    }

    @Transactional
    @Override
    public ProductReadDto save(ProductCreateEditDto productCreateEditDto) {

        Category category = categoryRepository.findById(productCreateEditDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + productCreateEditDto.getCategoryId()));

        var product = productCreateEditDtoMapper.mapFrom(productCreateEditDto);
        product.setCategory(category);
        product.setCreationDate(LocalDateTime.now());
        var savedProduct = productRepository.save(product);

        return productReadDtoMapper.mapFrom(savedProduct);
    }

    @Transactional
    @Override
    public ProductReadDto update(Long id, ProductCreateEditDto productCreateEditDto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        Category category = categoryRepository.findById(productCreateEditDto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + productCreateEditDto.getCategoryId()));

        productCreateEditDtoMapper.mapTo(productCreateEditDto, product);
        product.setCategory(category);
        product.setLastUpdateDate(LocalDateTime.now());
        var updatedProduct = productRepository.save(product);

        return productReadDtoMapper.mapFrom(updatedProduct);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        productRepository.deleteById(id);
    }
}
