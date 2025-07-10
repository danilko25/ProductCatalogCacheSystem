package com.danilko.product_catalog_cache_system.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("CategoryReadDto")
@Schema(description = "Category information")
public class CategoryReadDto implements Serializable {

    @Schema(description = "Unique category identifier")
    private Long id;

    @Schema(description = "Category name")
    private String categoryName;
}
