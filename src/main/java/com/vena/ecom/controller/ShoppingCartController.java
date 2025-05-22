package com.vena.ecom.controller;

import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.ShoppingCart;
import com.vena.ecom.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping
    public ResponseEntity<ShoppingCart> viewCart(@RequestParam String customerId) {

        return ResponseEntity.ok(shoppingCartService.getCartByCustomerId(customerId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItem> addCartItem(@RequestParam String customerId,
            @RequestBody com.vena.ecom.dto.AddCartItemRequest request) {

        return ResponseEntity.ok(shoppingCartService.addCartItem(customerId, request));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemQuantity(@PathVariable String cartItemId,
            @RequestBody com.vena.ecom.dto.UpdateCartItemRequest request) {
        return ResponseEntity.ok(shoppingCartService.updateCartItemQuantity(cartItemId, request));
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
