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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository productRepository;
    private final CategoryJpaRepository categoryRepository;
    private final ProductReadDtoMapper  productReadDtoMapper;
    private final ProductCreateEditMapper productCreateEditDtoMapper;

    private static final String PRODUCT_LIST_CACHE = "productList";
    private static final String SINGLE_PRODUCT_CACHE = "product";

    @Override
    public Page<ProductReadDto> findAll(Pageable pageable) {
        var products = productRepository.findAllWithCategory(pageable);
        return products.map(productReadDtoMapper::mapFrom);
    }

    @Cacheable(value = PRODUCT_LIST_CACHE, key = "#id")
    @Override
    public List<ProductReadDto> findByCategoryId(Long id) {
        var productsByCategory = productRepository.findAllByCategoryId(id);
        return productsByCategory.stream().map(productReadDtoMapper::mapFrom).toList();
    }

    @Cacheable(value = SINGLE_PRODUCT_CACHE, key = "#id")
    @Override
    public ProductReadDto findById(Long id) {
        var productOptional = productRepository.findById(id);
        return productOptional.map(productReadDtoMapper::mapFrom).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
    }

    @CacheEvict(value = PRODUCT_LIST_CACHE,  key = "#productCreateEditDto.categoryId")
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

    @CacheEvict(value = PRODUCT_LIST_CACHE,  key = "#productCreateEditDto.categoryId")
    @CachePut(value = SINGLE_PRODUCT_CACHE, key = "#id")
    @Transactional
    @Override
    public ProductReadDto update(Long id, ProductCreateEditDto productCreateEditDto) throws MethodArgumentNotValidException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));

        if (product.getCategory().getId() != productCreateEditDto.getCategoryId()) {
            BindingResult bindingResult = new BeanPropertyBindingResult(productCreateEditDto, "productCreateEditDto");
            bindingResult.addError(new FieldError(
                    "productCreateEditDto",
                    "categoryId",
                    productCreateEditDto.getCategoryId(),
                    false,
                    null,
                    null,
                    "Category ID cannot be changed for an existing product."
            ));

            throw new MethodArgumentNotValidException(null, bindingResult);
        }

        productCreateEditDtoMapper.mapTo(productCreateEditDto, product);
        product.setLastUpdateDate(LocalDateTime.now());
        var updatedProduct = productRepository.save(product);

        return productReadDtoMapper.mapFrom(updatedProduct);
    }

    @Caching(evict = {
            @CacheEvict(value = SINGLE_PRODUCT_CACHE, key = "#id"),
            @CacheEvict(value = PRODUCT_LIST_CACHE, allEntries = true)
    })
    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + id));
        productRepository.deleteById(id);
    }
}
