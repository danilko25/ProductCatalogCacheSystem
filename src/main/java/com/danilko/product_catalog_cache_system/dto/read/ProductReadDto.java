package com.danilko.product_catalog_cache_system.dto.read;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReadDto {
    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private CategoryReadDto category;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
