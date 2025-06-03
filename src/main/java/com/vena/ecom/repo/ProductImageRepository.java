package com.vena.ecom.repo;

import com.vena.ecom.model.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {
    boolean existsByVendorProductIdAndImageUrl(String vendorProductId, String imageUrl);
}