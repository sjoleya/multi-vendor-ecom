package com.vena.ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
