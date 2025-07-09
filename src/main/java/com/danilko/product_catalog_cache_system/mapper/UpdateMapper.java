package com.danilko.product_catalog_cache_system.mapper;

public interface UpdateMapper <F, T>{
    void mapTo(F from, T to);
}
