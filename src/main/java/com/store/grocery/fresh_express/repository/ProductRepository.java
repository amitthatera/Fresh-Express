package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.Category;
import com.store.grocery.fresh_express.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
}
