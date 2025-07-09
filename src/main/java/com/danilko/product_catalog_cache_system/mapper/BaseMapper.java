package com.danilko.product_catalog_cache_system.mapper;

public interface BaseMapper<F, T>{
    T mapFrom(F entity);
}
