package com.vena.ecom.repo;

import com.vena.ecom.model.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, String> {

    List<CartItem> findByCart_Id(String cartId);

    List<CartItem> findByVendorProduct_Id(String vendorProductId);
}