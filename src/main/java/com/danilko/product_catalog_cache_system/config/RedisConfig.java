package com.danilko.product_catalog_cache_system.config;


import com.danilko.product_catalog_cache_system.dto.read.CategoryReadDto;
import com.danilko.product_catalog_cache_system.dto.read.ProductReadDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class RedisConfig {


    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // For ProductReadDto
        Jackson2JsonRedisSerializer<ProductReadDto> productSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, ProductReadDto.class);
        RedisCacheConfiguration productConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(productSerializer));

        // For List<ProductReadDto>
        JavaType productListType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, ProductReadDto.class);
        Jackson2JsonRedisSerializer<List<ProductReadDto>> productListSerializer =
                new Jackson2JsonRedisSerializer<>(productListType);
        RedisCacheConfiguration productListConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(productListSerializer));

        // For CategoryReadDto
        Jackson2JsonRedisSerializer<CategoryReadDto> categorySerializer =
                new Jackson2JsonRedisSerializer<>(CategoryReadDto.class);
        RedisCacheConfiguration categoryConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(categorySerializer));

        // For List<CategoryReadDto>
        JavaType categoryListType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, CategoryReadDto.class);
        Jackson2JsonRedisSerializer<List<CategoryReadDto>> categoryListSerializer =
                new Jackson2JsonRedisSerializer<>(categoryListType);
        RedisCacheConfiguration categoryListConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(categoryListSerializer));

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration("product", productConfig)
                .withCacheConfiguration("products", productListConfig)
                .withCacheConfiguration("category", categoryConfig)
                .withCacheConfiguration("categories", categoryListConfig)
                .build();
    }
}