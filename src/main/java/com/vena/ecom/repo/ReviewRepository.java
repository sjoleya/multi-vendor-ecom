package com.vena.ecom.repo;

import com.vena.ecom.model.Review;
import com.vena.ecom.model.VendorProduct;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    long countByOrderItem_VendorProduct(VendorProduct vendorProduct);

    List<Review> findByVendorProductId(String id);
}
