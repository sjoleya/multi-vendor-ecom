package com.vena.ecom.service.impl;

import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.repo.CartItemRepository;
import com.vena.ecom.repo.ShoppingCartRepository;

import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.ShoppingCart;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ShoppingCart getCartByCustomerId(String customerId) {
        return shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseGet(() -> {
                    User customer = userRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("User not found"));

                    ShoppingCart cart = new ShoppingCart();
                    cart.setCustomer(customer);
                    return shoppingCartRepository.save(cart);
                });
    }

    @Override
    public CartItem addCartItem(String customerId, com.vena.ecom.dto.AddCartItemRequest request) {
        ShoppingCart cart = getCartByCustomerId(customerId);

        VendorProduct product = vendorProductRepository.findById(request.getVendorProductId())
                .orElseThrow(() -> new RuntimeException("Vendor product not found"));

        CartItem cartItem = new CartItem(null, product, request.getQuantity());
        cart.getCartItems().add(cartItem);
        shoppingCartRepository.save(cart);

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItemQuantity(String cartItemId, com.vena.ecom.dto.UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(request.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(String customerId) {
        ShoppingCart cart = getCartByCustomerId(customerId);
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart); // CascadeType.ALL ensures related items are deleted
    }
}
