package com.vena.ecom.service;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.response.CartItemResponse;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart getCartByCustomerId(String customerId);

    CartItemResponse addCartItem(String customerId, AddCartItemRequest request);

    CartItemResponse updateCartItemQuantity(String cartItemId, UpdateCartItemRequest request);

    void removeCartItem(String cartItemId);

    void clearCart(String customerId);

}
