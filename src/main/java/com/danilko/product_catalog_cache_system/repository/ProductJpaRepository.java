package com.danilko.product_catalog_cache_system.repository;

import com.danilko.product_catalog_cache_system.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @NonNull
    Optional<Product> findById(@NonNull Long id);

    @NonNull
    Page<Product> findAllByCategoryId(@NonNull Long id,  @NonNull Pageable pageable);

}
