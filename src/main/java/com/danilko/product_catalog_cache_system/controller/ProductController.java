package com.danilko.product_catalog_cache_system.controller;

import com.danilko.product_catalog_cache_system.dto.create_edit.ProductCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import com.danilko.product_catalog_cache_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductReadDto>> getAllProducts(Pageable pageable) {
        return  ResponseEntity.ok().body(productService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<ProductReadDto> createProduct(@RequestBody @Valid ProductCreateEditDto productCreateEditDto) {
        var createdProduct = productService.save(productCreateEditDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        return  ResponseEntity.created(location).body(createdProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReadDto> getProductById(@PathVariable Long id) {
        return  ResponseEntity.ok().body(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReadDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductCreateEditDto productCreateEditDto) throws MethodArgumentNotValidException {
          return  ResponseEntity.ok().body(productService.update(id, productCreateEditDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductReadDto> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<List<ProductReadDto>> getProductsByCategory(@PathVariable Long category_id) {
        return  ResponseEntity.ok().body(productService.findByCategoryId(category_id));
    }
}
