package com.danilko.product_catalog_cache_system.repository;

import com.danilko.product_catalog_cache_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    @NonNull
    Optional<Category> findById(@NonNull Long id);

    @NonNull
    Optional<Category> findByName(@NonNull String name);
}
