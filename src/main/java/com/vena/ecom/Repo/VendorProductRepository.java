package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendorProductRepository extends JpaRepository<VendorProduct, String> {
    List<VendorProduct> findByVendorId_Id(String vendorId);
}
