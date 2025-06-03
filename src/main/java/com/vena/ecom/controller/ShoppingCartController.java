package com.vena.ecom.controller;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.dto.response.CartItemResponse;
import com.vena.ecom.service.ShoppingCartService;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
public class ShoppingCartController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<ShoppingCartResponse> viewCart(@RequestParam String customerId) {
        logger.info("GET /customer/cart - Viewing cart for customerId: {}", customerId);
        ShoppingCartResponse response = shoppingCartService.getCartByCustomerId(customerId);
        logger.info("Cart retrieved successfully for customerId: {}", customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestParam String customerId,
            @Valid @RequestBody AddCartItemRequest request) {
        logger.info("POST /customer/cart/items - Adding productId: {} to cart for customerId: {}",
                request.getVendorProductId(), customerId);
        CartItemResponse response = shoppingCartService.addCartItem(customerId, request);
        logger.info("Item added successfully to cart for customerId: {}", customerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@PathVariable String cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        logger.info("PUT /customer/cart/items/{} - Updating quantity to {}", cartItemId, request.getQuantity());
        CartItemResponse response = shoppingCartService.updateCartItemQuantity(cartItemId, request);
        logger.info("Cart item updated successfully for cartItemId: {}", cartItemId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String cartItemId) {
        logger.info("DELETE /customer/cart/items/{} - Removing cart item", cartItemId);
        shoppingCartService.removeCartItem(cartItemId);
        logger.info("Cart item removed successfully: {}", cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam String customerId) {
        logger.info("DELETE /customer/cart - Clearing cart for customerId: {}", customerId);
        shoppingCartService.clearCart(customerId);
        logger.info("Cart cleared successfully for customerId: {}", customerId);
        return ResponseEntity.ok().build();
    }
}
