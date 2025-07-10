package com.danilko.product_catalog_cache_system.repository;

import com.danilko.product_catalog_cache_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    @NonNull
    Optional<Product> findById(@NonNull Long id);

    @NonNull
    List<Product> findAllByCategoryId(@NonNull Long id);

}
