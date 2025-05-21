package com.vena.ecom.repo;

import com.vena.ecom.model.Order;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findTopByCustomerEmailOrderByOrderDateDesc(String email);

    List<Order> findByCustomer_UserId(String customerId);

    List<Order> findByVendorProduct_VendorProfile_VendorId(String vendorId);
}
