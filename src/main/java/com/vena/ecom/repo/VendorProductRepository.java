package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.ProductCatalog;

import java.util.List;
import java.util.Optional;

public interface VendorProductRepository extends JpaRepository<VendorProduct, String> {
    List<VendorProduct> findByVendorId_Id(String vendorId);

    Optional<VendorProduct> findByCatalogProductId(ProductCatalog catalogProductId);
}
