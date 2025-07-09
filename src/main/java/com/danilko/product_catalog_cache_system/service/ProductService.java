package com.danilko.product_catalog_cache_system.service;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<ProductReadDto> findAll(Pageable pageable);
    Page<ProductReadDto> findByCategoryId(Long id, Pageable pageable);
    Optional<ProductReadDto> findById(Long id);
    ProductReadDto save(ProductCreateEditDto productCreateEditDto);
    ProductReadDto update(Long id, ProductCreateEditDto productCreateEditDto);
    void  deleteById(Long id);
}
