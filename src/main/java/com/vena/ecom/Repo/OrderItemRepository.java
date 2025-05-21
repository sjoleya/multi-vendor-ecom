package com.vena.ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

}
