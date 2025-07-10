package com.danilko.product_catalog_cache_system.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("ProductReadDto")
@Schema(description = "Full product information")
public class ProductReadDto implements Serializable {

    @Schema(description = "Unique product identifier")
    private Long id;

    @Schema(description = "Product name")
    private String productName;

    @Schema(description = "Product description")
    private String productDescription;

    @Schema(description = "Product price", example = "999.99")
    private BigDecimal price;

    @Schema(description = "Category object to which the product belongs", implementation = CategoryReadDto.class)
    private CategoryReadDto category;

    @Schema(description = "Product stock quantity")
    private Integer stock;

    @Schema(description = "Date and time of product creation")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time of last product update")
    private LocalDateTime updatedAt;
}
