package com.vena.ecom.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.Address;
import com.vena.ecom.model.enums.AddressType;

public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByUserEmailAndAddressType(String email, AddressType addressType);

    List<Address> findByUserUserId(String userId);
}
