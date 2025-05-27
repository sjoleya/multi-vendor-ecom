package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VendorProductRepository extends JpaRepository<VendorProduct, String> {
    List<VendorProduct> findByVendorProfileId(String vendorId);

    Optional<VendorProduct> findByProductCatalogId(String catalogProductId);

    List<VendorProduct> findAllByProductCatalogCategoryName(String categoryName);
}
