package com.danilko.product_catalog_cache_system.dto.create_edit;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Data for creating or updating a category")
public class CategoryCreateEditDto {

    @Schema(description = "Category name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    @Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters long.")
    private String categoryName;
}
