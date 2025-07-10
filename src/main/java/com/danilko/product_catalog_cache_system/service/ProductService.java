package com.danilko.product_catalog_cache_system.service;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface ProductService {
    Page<ProductReadDto> findAll(Pageable pageable);
    List<ProductReadDto> findByCategoryId(Long id);
    ProductReadDto findById(Long id);
    ProductReadDto save(ProductCreateEditDto productCreateEditDto);
    ProductReadDto update(Long id, ProductCreateEditDto productCreateEditDto) throws MethodArgumentNotValidException;
    void  deleteById(Long id);
}