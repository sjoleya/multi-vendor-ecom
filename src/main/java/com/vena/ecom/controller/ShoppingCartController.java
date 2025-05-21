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
    public ResponseEntity<ShoppingCart> viewCart(){
        String customerId ="1";
        return ResponseEntity.ok(shoppingCartService.getCartByCustomerId(customerId));
    }
    @PostMapping("/items")
    public ResponseEntity<CartItem> addCartItem(@RequestParam String vendorProductId, @RequestParam Integer quantity){
        String customerId = "1";
        return ResponseEntity.ok(shoppingCartService.addCartItem(customerId ,vendorProductId , quantity));
    }

    @PutMapping("/items/{cartItemId}")
    public ResponseEntity<CartItem>updateCartItemQuantity(@PathVariable String cartItemId , @RequestParam Integer quantity ) {
        return ResponseEntity.ok(shoppingCartService.updateCartItemQuantity(cartItemId, quantity));
    }
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable String cartItemId) {
        shoppingCartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        String customerId = "1"; // Placeholder
        shoppingCartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}
