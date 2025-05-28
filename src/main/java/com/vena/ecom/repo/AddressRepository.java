package com.vena.ecom.repo;

import com.vena.ecom.model.Address;
import com.vena.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByUser(User user);
}
