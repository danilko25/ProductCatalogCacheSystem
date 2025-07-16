package com.danilko.product_catalog_cache_system.mapper.createEditMapper;


import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCreateEditMapperTest {

    private CategoryCreateEditMapper categoryCreateEditMapper;

    @BeforeEach
    void setUp() {
        categoryCreateEditMapper = new CategoryCreateEditMapper();
    }

    @Test
    void testMapFrom_ValidDto() {
        CategoryCreateEditDto dto = new CategoryCreateEditDto("New Category Name");

        Category category = categoryCreateEditMapper.mapFrom(dto);

        assertNotNull(category);
        assertEquals(dto.getCategoryName(), category.getName());
        assertNull(category.getId());
    }

    @Test
    void testMapTo_ValidDtoAndUpdateExistingEntity() {
        Category existingCategory = Category.builder()
                .id(1L)
                .name("Old Category Name")
                .build();

        CategoryCreateEditDto dto = new CategoryCreateEditDto("Updated Category Name");

        categoryCreateEditMapper.mapTo(dto, existingCategory);

        assertEquals(dto.getCategoryName(), existingCategory.getName());
        assertEquals(1L, existingCategory.getId());
    }
}

