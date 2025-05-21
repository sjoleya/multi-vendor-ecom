package com.vena.ecom.repo;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
    // Custom derived query to get all orders placed by a customer
    List<Order> findByCustomer_UserId(String userId);
}
