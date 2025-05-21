package com.vena.ecom.service;

import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart getCartByCustomerId(String customerId);

    CartItem addCartItem(String cartItem, String vendorProductId, Integer quantity);

    CartItem updateCartItemQuantity(String cartItemId, Integer quantity);

    void removeCartItem(String cartItemId);

    void clearCart(String customerId);

}
