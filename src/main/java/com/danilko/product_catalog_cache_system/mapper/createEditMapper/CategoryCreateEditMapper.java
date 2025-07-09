package com.danilko.product_catalog_cache_system.mapper.createEditMapper;

import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.mapper.BaseMapper;
import com.danilko.product_catalog_cache_system.mapper.UpdateMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryCreateEditMapper implements BaseMapper<CategoryCreateEditDto, Category>, UpdateMapper<CategoryCreateEditDto, Category> {
    @Override
    public Category mapFrom(CategoryCreateEditDto categoryCreateEditDto) {
        Category category = new Category();
        category.setName(categoryCreateEditDto.getCategoryName());
        return category;
    }

    @Override
    public void mapTo(CategoryCreateEditDto categoryCreateEditDto, Category category) {
        category.setName(categoryCreateEditDto.getCategoryName());
    }
}

