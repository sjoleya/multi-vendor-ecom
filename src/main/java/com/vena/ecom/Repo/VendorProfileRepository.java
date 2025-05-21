package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendorProfileRepository extends JpaRepository<VendorProfile, String> {

    Optional<VendorProfile> findByStoreName(String storeName);

}
