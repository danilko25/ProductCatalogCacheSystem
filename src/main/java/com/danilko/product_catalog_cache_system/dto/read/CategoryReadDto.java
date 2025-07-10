package com.danilko.product_catalog_cache_system.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("CategoryReadDto")
public class CategoryReadDto implements Serializable {
    private Long id;
    private String categoryName;
}
