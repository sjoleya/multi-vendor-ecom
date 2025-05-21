package com.vena.ecom.repo;

import java.util.List;

import com.vena.ecom.model.ProductCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, String> {
    List<ProductCatalog> findByProductCategoryCategoryId(String categoryId);
}
