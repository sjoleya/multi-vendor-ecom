package com.vena.ecom.repo;

import java.util.Optional;

import com.vena.ecom.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProfileRepository extends JpaRepository<VendorProfile, String> {
    Optional<VendorProfile> findByUser_Id(String userId);
}
