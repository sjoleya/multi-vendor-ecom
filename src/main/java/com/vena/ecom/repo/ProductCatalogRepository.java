package com.vena.ecom.repo;

import com.vena.ecom.model.ProductCatalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, String> {
    List<ProductCatalog> findByCategory_Id(String categoryId);

    java.util.Optional<ProductCatalog> findByName(String name);

    java.util.Optional<ProductCatalog> findByGlobalSKU(String globalSKU);
}
