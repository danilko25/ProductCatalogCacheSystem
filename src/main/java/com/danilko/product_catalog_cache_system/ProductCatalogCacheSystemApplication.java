package com.danilko.product_catalog_cache_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class ProductCatalogCacheSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductCatalogCacheSystemApplication.class, args);
	}

}
