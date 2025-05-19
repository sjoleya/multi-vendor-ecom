package com.vena.ecom.repo;

import com.vena.ecom.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    Optional<ProductCategory> findByName(String name);

}
