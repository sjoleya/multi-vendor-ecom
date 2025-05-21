package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorProductRepository extends JpaRepository<VendorProduct, String> {
    Optional<VendorProduct> findBySKU(String sku);
}
