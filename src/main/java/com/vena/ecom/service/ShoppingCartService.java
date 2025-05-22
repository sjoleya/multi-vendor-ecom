package com.vena.ecom.service;

import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart getCartByCustomerId(String customerId);

    CartItem addCartItem(String customerId, com.vena.ecom.dto.AddCartItemRequest request);

    CartItem updateCartItemQuantity(String cartItemId, com.vena.ecom.dto.UpdateCartItemRequest request);

    void removeCartItem(String cartItemId);

    void clearCart(String customerId);

}
