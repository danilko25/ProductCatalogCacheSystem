package com.danilko.product_catalog_cache_system.dto.create_edit;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductCreateEditDto {
    @NotBlank(message = "Product name couldn't be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters long.")
    private String productName;
    @Size(max = 300, message = "Product description too long")
    private String productDescription;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price has to be greater than 0")
    private BigDecimal price;
    private Long categoryId;
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stock;
}
