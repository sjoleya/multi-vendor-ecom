package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.dto.response.CartItemResponse;
import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.*;
import com.vena.ecom.repo.*;
import com.vena.ecom.service.ShoppingCartService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Autowired private ShoppingCartRepository shoppingCartRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Autowired private VendorProductRepository vendorProductRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public ShoppingCartResponse getCartByCustomerId(String customerId) {
        validateCustomerId(customerId);
        logger.info("Fetching cart for customerId: {}", customerId);

        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    logger.info("Cart not found, creating new for customerId: {}", customerId);
                    User customer = userRepository.findById(customerId)
                            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + customerId));
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setCustomer(customer);
                    return shoppingCartRepository.save(newCart);
                });

        return new ShoppingCartResponse(cart);
    }

    @Override
    public CartItemResponse addCartItem(String customerId, AddCartItemRequest request) {
        validateCustomerId(customerId);
        validateAddCartItemRequest(request);

        logger.info("Adding product '{}' to cart for customerId: {}", request.getVendorProductId(), customerId);

        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customerId: " + customerId));

        VendorProduct product = vendorProductRepository.findById(request.getVendorProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor product not found with ID: " + request.getVendorProductId()));

        CartItem cartItem = new CartItem(null, product, request.getQuantity());
        cart.getCartItems().add(cartItem);

        shoppingCartRepository.save(cart);
        cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(String cartItemId, UpdateCartItemRequest request) {
        validateCartItemId(cartItemId);
        validateUpdateCartItemRequest(request);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + cartItemId));

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        return new CartItemResponse(cartItem);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        validateCartItemId(cartItemId);
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public void clearCart(String customerId) {
        validateCustomerId(customerId);
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for customerId: " + customerId));

        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);
    }

    private void validateCustomerId(String customerId) {
        if (!StringUtils.hasText(customerId)) {
            throw new IllegalArgumentException("Customer ID must not be empty");
        }
    }

    private void validateCartItemId(String cartItemId) {
        if (!StringUtils.hasText(cartItemId)) {
            throw new IllegalArgumentException("Cart item ID must not be empty");
        }
    }

    private void validateAddCartItemRequest(AddCartItemRequest request) {
        if (request == null || !StringUtils.hasText(request.getVendorProductId()) || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid add cart item request: product ID and positive quantity required");
        }
    }

    private void validateUpdateCartItemRequest(UpdateCartItemRequest request) {
        if (request == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid update cart item request: positive quantity required");
        }
    }
}
