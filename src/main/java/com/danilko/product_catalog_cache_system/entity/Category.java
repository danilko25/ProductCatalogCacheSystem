package com.danilko.product_catalog_cache_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Category extends BaseEntity<Long> {

    @Column(name = "category_name", nullable = false)
    private String name;


    public String toString() {
        return "Category(super=" + this.getId() + ", name=" + this.getName() + ")";
    }
}
