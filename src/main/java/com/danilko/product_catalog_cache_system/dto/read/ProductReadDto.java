package com.danilko.product_catalog_cache_system.dto.read;

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
public class ProductReadDto implements Serializable {
    private Long id;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private CategoryReadDto category;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
