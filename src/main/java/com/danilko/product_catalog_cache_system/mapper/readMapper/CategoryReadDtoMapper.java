package com.danilko.product_catalog_cache_system.mapper.readMapper;

import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.mapper.BaseMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryReadDtoMapper implements BaseMapper<Category, CategoryReadDto> {
    @Override
    public CategoryReadDto mapFrom(Category entity) {
        return new CategoryReadDto(entity.getId(), entity.getName());
    }
}
