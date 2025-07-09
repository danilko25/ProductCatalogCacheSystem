package com.danilko.product_catalog_cache_system.repository;

import com.danilko.product_catalog_cache_system.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ActiveProfiles("test")
class CategoryJpaRepositoryTest {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void whenFindById_thenReturnCategory() {
        // Name 'Test Electronics' from V100__insert_test_data.sql
        Long testCategoryId = 100L;

        Optional<Category> foundCategory = categoryJpaRepository.findById(testCategoryId);
        assertThat(foundCategory).isPresent();
        assertAll(
                () -> assertThat(foundCategory.get().getId()).isEqualTo(testCategoryId),
                () -> assertThat(foundCategory.get().getName()).isEqualTo("Test Electronics")
        );
    }

    @Test
    void whenFindByName_thenReturnCategory() {
        // Name 'Test Books' from V100__insert_test_data.sql
        String categoryName = "Test Books";

        Optional<Category> foundCategory = categoryJpaRepository.findByName(categoryName);
        assertThat(foundCategory).isPresent();
        assertAll(
                () -> assertThat(foundCategory.get().getName()).isEqualTo(categoryName),
                () -> assertThat(foundCategory.get().getId()).isEqualTo(101L)
        );
    }

    @Test
    void whenFindById_thenNotFound() {
        // An ID that doesn't exist in V100__insert_test_data.sql
        Long nonExistentId = 999L;

        Optional<Category> foundCategory = categoryJpaRepository.findById(nonExistentId);
        assertThat(foundCategory).isNotPresent();
    }

    @Test
    void whenFindByName_thenNotFound() {
        // A Name that doesn't exist in V100__insert_test_data.sql
        String nonExistentName = "Non Existent Category";

        Optional<Category> foundCategory = categoryJpaRepository.findByName(nonExistentName);
        assertThat(foundCategory).isNotPresent();
    }

    @Test
    void whenFindAllCategories_thenReturnPageOfCategories() {
        Pageable pageable = PageRequest.of(0, 2);
        // V100__insert_test_data.sql contains three categories
        Page<Category> categoryPage = categoryJpaRepository.findAll(pageable);

        categoryPage.forEach(System.out::println);

        assertThat(categoryPage).isNotNull();
        assertThat(categoryPage.getTotalElements()).isEqualTo(3);
        assertThat(categoryPage.getTotalPages()).isEqualTo(2);
        assertThat(categoryPage.getNumber()).isEqualTo(0);
        assertThat(categoryPage.getNumberOfElements()).isEqualTo(2);
        assertThat(categoryPage.getContent()).hasSize(2);
    }

    @Test
    void whenSaveCategory_thenCategoryIsPersisted() {
        Category newCategory = new Category();
        newCategory.setName("New Saved Category");

        Category savedCategory = categoryJpaRepository.save(newCategory);
        entityManager.flush();

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("New Saved Category");

        Optional<Category> found = categoryJpaRepository.findById(savedCategory.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("New Saved Category");
    }

    @Test
    void whenUpdateCategory_thenCategoryIsModified() {
        Long categoryIdToUpdate = 100L;
        Optional<Category> existingCategoryOpt = categoryJpaRepository.findById(categoryIdToUpdate);
        assertThat(existingCategoryOpt).isPresent();
        Category categoryToUpdate = existingCategoryOpt.get();

        String newName = "Updated Electronics Name";
        categoryToUpdate.setName(newName);

        Category updatedCategory = categoryJpaRepository.save(categoryToUpdate);
        entityManager.flush();

        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getId()).isEqualTo(categoryIdToUpdate);
        assertThat(updatedCategory.getName()).isEqualTo(newName);

        Optional<Category> foundUpdated = categoryJpaRepository.findById(categoryIdToUpdate);
        assertThat(foundUpdated).isPresent();
        assertThat(foundUpdated.get().getName()).isEqualTo(newName);
    }
}