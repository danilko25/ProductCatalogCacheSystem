package com.danilko.product_catalog_cache_system.mapper.readMapper;

import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ProductReadDtoMapperTest {

    @InjectMocks
    private ProductReadDtoMapper productReadDtoMapper;

    @Mock
    private CategoryReadDtoMapper categoryMapper;

    private Product product;
    private Category category;
    private CategoryReadDto categoryReadDto;


    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        product = new Product();
        product.setId(101L);
        product.setName("Laptop X1");
        product.setDescription("High-performance laptop");
        product.setPrice(new BigDecimal("1200.00"));
        product.setCategory(category);
        product.setStock(50);
        product.setCreationDate(LocalDateTime.now().minusDays(7));
        product.setLastUpdateDate(LocalDateTime.now().minusDays(2));

        // Will be returned by Mockito
        categoryReadDto = new CategoryReadDto(category.getId(), category.getName());
    }

    @Test
    void testMapFrom_ValidProduct() {
        //Mock CategoryReadDtoMapper
        when(categoryMapper.mapFrom(category)).thenReturn(categoryReadDto);

        ProductReadDto resultDto = productReadDtoMapper.mapFrom(product);

        assertNotNull(resultDto);
        assertEquals(product.getId(), resultDto.getId());
        assertEquals(product.getName(), resultDto.getProductName());
        assertEquals(product.getDescription(), resultDto.getProductDescription());
        assertEquals(product.getPrice(), resultDto.getPrice());
        assertEquals(product.getStock(), resultDto.getStock());
        assertEquals(product.getCreationDate(), resultDto.getCreatedAt());
        assertEquals(product.getLastUpdateDate(), resultDto.getUpdatedAt());

        assertNotNull(resultDto.getCategory());
        assertEquals(categoryReadDto.getId(), resultDto.getCategory().getId());
        assertEquals(categoryReadDto.getCategoryName(), resultDto.getCategory().getCategoryName());
    }

    @Test
    void testMapFrom_ProductWithNullCategory() {
        product.setCategory(null);

        when(categoryMapper.mapFrom(null)).thenReturn(null);

        ProductReadDto resultDto = productReadDtoMapper.mapFrom(product);

        assertNotNull(resultDto);
        assertEquals(product.getId(), resultDto.getId());
        assertEquals(product.getName(), resultDto.getProductName());
        assertEquals(product.getDescription(), resultDto.getProductDescription());
        assertEquals(product.getPrice(), resultDto.getPrice());
        assertEquals(product.getStock(), resultDto.getStock());
        assertEquals(product.getCreationDate(), resultDto.getCreatedAt());
        assertEquals(product.getLastUpdateDate(), resultDto.getUpdatedAt());

        assertEquals(null, resultDto.getCategory());
    }
}
