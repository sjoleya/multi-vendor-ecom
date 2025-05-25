package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
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

import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.dto.response.CartItemResponse;

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
    public ShoppingCartResponse getCartByCustomerId(String customerId) {
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseGet(() -> {
                    User customer = userRepository.findById(customerId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setCustomer(customer);
                    return shoppingCartRepository.save(newCart);
                });
        return new ShoppingCartResponse(cart);
    }

    @Override
    public CartItemResponse addCartItem(String customerId, AddCartItemRequest request) {
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        VendorProduct product = vendorProductRepository.findById(request.getVendorProductId())
                .orElseThrow(() -> new RuntimeException("Vendor product not found"));
        CartItem cartItem = new CartItem(null, product, request.getQuantity());
        cart.getCartItems().add(cartItem);
        shoppingCartRepository.save(cart);
        cartItemRepository.save(cartItem);
        return new CartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(String cartItemId, UpdateCartItemRequest request) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        return new CartItemResponse(cartItem);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(String customerId) {
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart); // CascadeType.ALL ensures related items are deleted
    }
}
