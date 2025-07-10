package com.danilko.product_catalog_cache_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Catalog Cache System",
                version = "1.0",
                description = "RESTful web service for managing a product catalog with caching capabilities to improve read performance. The system handle basic CRUD operations for products and implement appropriate caching strategies."

        )
)
public class SwaggerConfig {

}
