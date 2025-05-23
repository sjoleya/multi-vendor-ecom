package com.vena.ecom.controller;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.dto.response.CartItemResponse;

@RestController
@RequestMapping("/customer/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<ShoppingCartResponse> viewCart(@RequestParam String customerId) {
        ShoppingCartResponse response = shoppingCartService.getCartByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addCartItem(@RequestParam String customerId,
            @RequestBody AddCartItemRequest request) {
        CartItemResponse response = shoppingCartService.addCartItem(customerId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItemQuantity(@PathVariable String cartItemId,
            @RequestBody UpdateCartItemRequest request) {
        CartItemResponse response = shoppingCartService.updateCartItemQuantity(cartItemId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestParam String customerId) {

        shoppingCartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}
