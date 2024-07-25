package com.store.grocery.fresh_express.repository;

import com.store.grocery.fresh_express.model.Product;
import com.store.grocery.fresh_express.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySubCategory(SubCategory category);

    Product findByProductUUID(String uuid);
}
