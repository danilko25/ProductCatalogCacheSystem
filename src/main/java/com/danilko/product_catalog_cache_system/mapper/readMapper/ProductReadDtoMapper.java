package com.danilko.product_catalog_cache_system.mapper.readMapper;

import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import com.danilko.product_catalog_cache_system.entity.Product;
import com.danilko.product_catalog_cache_system.mapper.BaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductReadDtoMapper implements BaseMapper<Product, ProductReadDto> {

    private final CategoryReadDtoMapper categoryMapper;

    @Override
    public ProductReadDto mapFrom(Product entity) {

        return ProductReadDto.builder()
                .id(entity.getId())
                .productName(entity.getName())
                .productDescription(entity.getDescription())
                .price(entity.getPrice())
                .category(categoryMapper.mapFrom(entity.getCategory()))
                .stock(entity.getStock())
                .createdAt(entity.getCreationDate())
                .updatedAt(entity.getLastUpdateDate())
                .build();
    }
}
