package com.vena.ecom.repo;

import com.vena.ecom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    // Custom derived query to get all order items for a customer
    List<OrderItem> findByCustomer_UserId(String userId);

    List<OrderItem> findByVendorProduct_VendorProfile_VendorId(String vendorId);
}