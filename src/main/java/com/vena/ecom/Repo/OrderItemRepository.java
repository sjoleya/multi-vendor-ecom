package com.vena.ecom.repo;

import com.vena.ecom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
    // Custom derived query to get all order items for a customer
    List<OrderItem> findByOrder_Customer_Id(String userId);

    List<OrderItem> findByVendorProduct_VendorId_Id(String vendorId);
}
