package com.danilko.product_catalog_cache_system.repository;

import com.danilko.product_catalog_cache_system.entity.Category;
import com.danilko.product_catalog_cache_system.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
public class ProductJpaRepositoryTest {

    @Autowired
    private ProductJpaRepository productRepository;

    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindById_ExistingProduct() {
        Optional<Product> foundProduct = productRepository.findById(1000L);

        assertThat(foundProduct).isPresent();
        Product product = foundProduct.get();
        assertAll(
                () -> assertThat(product.getId()).isEqualTo(1000L),
                () -> assertThat(product.getName()).isEqualTo("Test Laptop Alpha"),
                () -> assertThat(product.getDescription()).isEqualTo("A basic test laptop for functional testing."),
                () -> assertThat(product.getPrice()).isEqualTo(new BigDecimal("500.00")),
                () -> assertThat(product.getStock()).isEqualTo(10),
                () -> assertThat(product.getCategory().getId()).isEqualTo(100L)
        );
    }

    @Test
    void testFindById_NonExistingProduct() {
        Optional<Product> foundProduct = productRepository.findById(9999L);
        assertThat(foundProduct).isNotPresent();
    }

    @Test
    void testSave_NewProduct() {
        Category electronicsCategory = categoryRepository.findById(100L).orElseThrow();

        Product newProduct = new Product();
        newProduct.setName("Test Monitor");
        newProduct.setDescription("A new test monitor.");
        newProduct.setPrice(new BigDecimal("300.00"));
        newProduct.setCategory(electronicsCategory);
        newProduct.setStock(50);
        var timestamp = LocalDateTime.now();
        newProduct.setCreationDate(timestamp);
        newProduct.setLastUpdateDate(timestamp);

        Product savedProduct = productRepository.save(newProduct);
        entityManager.flush();

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Test Monitor");


        Optional<Product> found = productRepository.findById(savedProduct.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Monitor");
        assertThat(found.get().getDescription()).isEqualTo("A new test monitor.");
        assertThat(found.get().getPrice()).isEqualTo(new BigDecimal("300.00"));
        assertThat(found.get().getCategory().getId()).isEqualTo(100L);
        assertThat(found.get().getStock()).isEqualTo(50);
        assertThat(found.get().getCreationDate()).isEqualTo(timestamp);
        assertThat(found.get().getLastUpdateDate()).isEqualTo(timestamp);
    }

    @Test
    void testUpdate_ExistingProduct() {
        // 1000, 'Test Laptop Alpha', 'A basic test laptop for functional testing.', 500.00, 100, 10, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())
        Optional<Product> productOptional = productRepository.findById(1000L);
        assertThat(productOptional).isPresent();
        Product productToUpdate = productOptional.get();

        productToUpdate.setName("Updated Laptop Name");
        productToUpdate.setPrice(new BigDecimal("550.00"));
        productToUpdate.setStock(5);
        var timestamp = LocalDateTime.now();
        productToUpdate.setLastUpdateDate(timestamp);

        Product updatedProduct = productRepository.save(productToUpdate);
        entityManager.flush();

        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getId()).isEqualTo(1000L);
        assertThat(updatedProduct.getName()).isEqualTo("Updated Laptop Name");
        assertThat(updatedProduct.getPrice()).isEqualTo(new BigDecimal("550.00"));
        assertThat(updatedProduct.getStock()).isEqualTo(5);

        Optional<Product> foundUpdated = productRepository.findById(1000L);
        assertThat(foundUpdated).isPresent();
        assertThat(foundUpdated.get().getName()).isEqualTo("Updated Laptop Name");
        assertThat(foundUpdated.get().getDescription()).isEqualTo("A basic test laptop for functional testing.");
        assertThat(foundUpdated.get().getPrice()).isEqualTo(new BigDecimal("550.00"));
        assertThat(foundUpdated.get().getCategory().getId()).isEqualTo(100L);
        assertThat(foundUpdated.get().getStock()).isEqualTo(5);
        assertThat(foundUpdated.get().getCreationDate()).isEqualTo(productToUpdate.getCreationDate());
        assertThat(foundUpdated.get().getLastUpdateDate()).isEqualTo(timestamp);

    }


    @Test
    void testDeleteById() {
        productRepository.deleteById(1000L);

        Optional<Product> deletedProduct = productRepository.findById(1000L);
        assertThat(deletedProduct).isNotPresent();
    }

    @Test
    void testFindAll_Pagination() {
        Pageable pageable = PageRequest.of(0, 3);

        Page<Product> productsPage = productRepository.findAll(pageable);

        assertThat(productsPage).isNotNull();
        assertThat(productsPage.getContent()).hasSize(3);
        // V100__insert_test_data.sql contains 8 products
        assertThat(productsPage.getTotalElements()).isEqualTo(8);
        assertThat(productsPage.getTotalPages()).isEqualTo(3); // 3 pages for 8 products
        assertThat(productsPage.getContent().get(0).getName()).isEqualTo("Test Laptop Alpha");
        assertThat(productsPage.getContent().get(2).getName()).isEqualTo("Test Headphones Gamma");
    }


    @Test
    void testFindAllByCategoryId_AllProductsForCategory100() {
        Long categoryId = 100L;

        List<Product> products = productRepository.findAllByCategoryId(categoryId);

        assertThat(products).isNotNull();
        assertThat(products).hasSize(4);

        assertThat(products.get(0).getName()).isEqualTo("Test Laptop Alpha");
        assertThat(products.get(1).getName()).isEqualTo("Test Phone Beta");
        assertThat(products.get(2).getName()).isEqualTo("Test Headphones Gamma");
        assertThat(products.get(3).getName()).isEqualTo("Test Out Of Stock Item");
    }

    @Test
    void testFindAllByCategoryId_NonExistingCategory() {
        Long nonExistingCategoryId = 999L;

        List<Product> products = productRepository.findAllByCategoryId(nonExistingCategoryId);

        assertThat(products).isNotNull();
        assertThat(products).isEmpty();
    }
}