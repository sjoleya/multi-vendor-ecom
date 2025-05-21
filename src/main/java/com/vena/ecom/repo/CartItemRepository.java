package com.vena.ecom.repo;

import com.vena.ecom.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository  extends JpaRepository<CartItem,String> {

}
