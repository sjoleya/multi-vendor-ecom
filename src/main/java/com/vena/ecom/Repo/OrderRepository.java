package com.vena.ecom.repo;

import com.vena.ecom.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,String> {
    Optional<Order> findTopByCustomerEmailOrderByOrderDateDesc(String email);

    List<Order> findByCustomer_UserId(String customerId);
}
