package com.danilko.product_catalog_cache_system.mapper.createEditMapper;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


class ProductCreateEditMapperTest {

    private ProductCreateEditMapper productCreateEditMapper;

    @BeforeEach
    void setUp() {
        productCreateEditMapper = new ProductCreateEditMapper();
    }


    @Test
    void testMapFrom_ValidDto() {
        ProductCreateEditDto dto = new ProductCreateEditDto(
                "New Product Name",
                "New Product Description",
                new BigDecimal("99.99"),
                1L,
                100
        );

        Product product = productCreateEditMapper.mapFrom(dto);

        assertNotNull(product);
        assertEquals(dto.getProductName(), product.getName());
        assertEquals(dto.getProductDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getStock(), product.getStock());

        assertNull(product.getId(), "ID should be null for a new entity before persistence");
        assertNull(product.getCategory(), "Category should be null as it's set by service layer");
        assertNull(product.getCreationDate(), "Creation date should be null as it's set by service layer");
        assertNull(product.getLastUpdateDate(), "Last update date should be null as it's set by service layer");
    }


    @Test
    void testMapTo_ValidDtoAndUpdateExistingEntity() {
        Category existingCategory = new Category(2L, "Existing Category");
        LocalDateTime existingCreationDate = LocalDateTime.now().minusDays(5);
        LocalDateTime existingUpdateDate = LocalDateTime.now().minusDays(1);

        Product existingProduct = new Product(
                10L,
                "Old Name",
                "Old Description",
                new BigDecimal("10.00"),
                existingCategory,
                5,
                existingCreationDate,
                existingUpdateDate
        );

        ProductCreateEditDto dto = new ProductCreateEditDto(
                "Updated Product Name",
                "Updated Product Description",
                new BigDecimal("150.75"),
                null, // categoryId is not directly used by copyProductParameters
                25
        );

        productCreateEditMapper.mapTo(dto, existingProduct);

        assertEquals(dto.getProductName(), existingProduct.getName());
        assertEquals(dto.getProductDescription(), existingProduct.getDescription());
        assertEquals(dto.getPrice(), existingProduct.getPrice());
        assertEquals(dto.getStock(), existingProduct.getStock());

        assertEquals(10L, existingProduct.getId(), "ID should remain unchanged");
        assertEquals(existingCategory, existingProduct.getCategory(), "Category should remain unchanged");
        assertEquals(existingCreationDate, existingProduct.getCreationDate(), "Creation date should remain unchanged");
        assertEquals(existingUpdateDate, existingProduct.getLastUpdateDate(), "Last update date should remain unchanged");
    }


    @Test
    void testMapTo_NullValuesInDto() {
        Product existingProduct = new Product(
                11L,
                "Old Product",
                "Old Desc",
                new BigDecimal("20.00"),
                new Category(3L, "Some Category"),
                10,
                LocalDateTime.now().minusDays(10),
                LocalDateTime.now().minusDays(2)
        );

        ProductCreateEditDto dto = new ProductCreateEditDto(
                "Product With Null Desc",
                null,
                new BigDecimal("30.00"),
                null,
                5
        );

        productCreateEditMapper.mapTo(dto, existingProduct);

        assertEquals(dto.getProductName(), existingProduct.getName());
        assertNull(existingProduct.getDescription(), "Description should be null if DTO's is null");
        assertEquals(dto.getPrice(), existingProduct.getPrice());
        assertEquals(dto.getStock(), existingProduct.getStock());

        assertEquals(11L, existingProduct.getId());
        assertNotNull(existingProduct.getCategory());
        assertNotNull(existingProduct.getCreationDate());
        assertNotNull(existingProduct.getLastUpdateDate());
    }
}