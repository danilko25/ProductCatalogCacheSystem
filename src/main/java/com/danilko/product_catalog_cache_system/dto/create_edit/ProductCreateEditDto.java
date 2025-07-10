package com.danilko.product_catalog_cache_system.dto.create_edit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Data for creating or updating a product")
public class ProductCreateEditDto {

    @Schema(description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Product name couldn't be empty")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters long.")
    private String productName;

    @Schema(description = "Product description", example = "Modern smartphone with a large screen and powerful camera.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 300, message = "Product description too long")
    private String productDescription;

    @Schema(description = "Product price", example = "999.99", requiredMode = Schema.RequiredMode.REQUIRED)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price has to be greater than 0")
    private BigDecimal price;

    @Schema(description = "ID of the category to which the product belongs", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long categoryId;

    @Schema(description = "Product stock quantity", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 0, message = "Stock cannot be negative.")
    private Integer stock;
}
