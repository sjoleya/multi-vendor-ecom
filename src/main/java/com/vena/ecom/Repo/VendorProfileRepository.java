package com.vena.ecom.repo;

import com.vena.ecom.model.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorProfileRepository extends JpaRepository<VendorProfile, String> {
    
}
