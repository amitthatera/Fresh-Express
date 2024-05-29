package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String name);
}
