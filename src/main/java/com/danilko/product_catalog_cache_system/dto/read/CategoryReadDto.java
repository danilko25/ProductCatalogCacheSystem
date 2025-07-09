package com.danilko.product_catalog_cache_system.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryReadDto {
    private Long id;
    private String categoryName;
}
