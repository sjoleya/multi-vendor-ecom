package com.vena.ecom.repo;

import com.vena.ecom.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    Optional<ShoppingCart> findByCustomer_Email(String email);

    Optional<ShoppingCart> findByCustomer_Id(String customerId);
}
