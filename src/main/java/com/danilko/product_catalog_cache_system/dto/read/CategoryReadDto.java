package com.danilko.product_catalog_cache_system.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReadDto{
    private Long id;
    private String categoryName;
}
