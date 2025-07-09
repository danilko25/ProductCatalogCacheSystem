package com.danilko.product_catalog_cache_system.mapper.createEditMapper;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.entity.Product;
import com.danilko.product_catalog_cache_system.mapper.BaseMapper;
import com.danilko.product_catalog_cache_system.mapper.UpdateMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateEditMapper implements BaseMapper<ProductCreateEditDto, Product>, UpdateMapper<ProductCreateEditDto, Product> {

    @Override
    public Product mapFrom(ProductCreateEditDto productCreateEditDto) {
        var product = new Product();
        copyProductParameters(productCreateEditDto, product);
        return product;
    }

    @Override
    public void mapTo(ProductCreateEditDto productCreateEditDto, Product product) {
        copyProductParameters(productCreateEditDto, product);
    }

    // This method doesn't use Category, CreationDate, and UpdateDate; the Service initializes them.
    private static void copyProductParameters(ProductCreateEditDto productCreateEditDto, Product product) {
        product.setName(productCreateEditDto.getProductName());
        product.setDescription(productCreateEditDto.getProductDescription());
        product.setPrice(productCreateEditDto.getPrice());
        product.setStock(productCreateEditDto.getStock());
    }
}
