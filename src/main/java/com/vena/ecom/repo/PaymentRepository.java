package com.vena.ecom.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vena.ecom.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
