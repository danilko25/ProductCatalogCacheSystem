package com.danilko.product_catalog_cache_system.mapper.readMapper;

import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryReadDtoMapperTest {

    private CategoryReadDtoMapper categoryReadDtoMapper;

    @BeforeEach
    void setUp() {
        categoryReadDtoMapper = new CategoryReadDtoMapper();
    }

    @Test
    void testMapFrom_ValidCategory() {
        Category category = new Category(1L, "Electronics");

        CategoryReadDto dto = categoryReadDtoMapper.mapFrom(category);

        assertNotNull(dto);
        assertEquals(category.getId(), dto.getId());
        assertEquals(category.getName(), dto.getCategoryName());
    }
}
