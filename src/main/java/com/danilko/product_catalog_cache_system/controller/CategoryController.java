package com.danilko.product_catalog_cache_system.controller;

import com.danilko.product_catalog_cache_system.dto.create_edit.CategoryCreateEditDto;
import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryReadDto>> getAllCategories() {
        return  ResponseEntity.ok().body(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryReadDto> createCategory(@RequestBody @Valid CategoryCreateEditDto categoryCreateEditDto) {
        var createdCategory = categoryService.save(categoryCreateEditDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return  ResponseEntity.created(location).body(createdCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReadDto> getCategory(@PathVariable Long id) {
        return  ResponseEntity.ok().body(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryReadDto> updateCategory(@RequestBody @Valid CategoryCreateEditDto categoryCreateEditDto,  @PathVariable Long id) {
        return  ResponseEntity.ok().body(categoryService.update(id, categoryCreateEditDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return  ResponseEntity.noContent().build();
    }
}
