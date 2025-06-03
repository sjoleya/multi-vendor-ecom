package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.*;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.repo.*;
import com.vena.ecom.service.ShoppingCartService;
import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.dto.response.CartItemResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

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
        logger.info("Fetching cart for customerId: {}", customerId);
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    logger.info("Cart not found for customerId: {}, creating new cart", customerId);
                    User customer = userRepository.findById(customerId)
                            .orElseThrow(() -> {
                                logger.error("User not found with customerId: {}", customerId);
                                return new ResourceNotFoundException("User not found with ID: " + customerId);
                            });
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setCustomer(customer);
                    return shoppingCartRepository.save(newCart);
                });
        logger.info("Cart fetched/created successfully for customerId: {}", customerId);
        return new ShoppingCartResponse(cart);
    }

    @Override
    public CartItemResponse addCartItem(String customerId, AddCartItemRequest request) {
        logger.info("Adding product '{}' to cart for customerId: {}", request.getVendorProductId(), customerId);
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> {
                    logger.error("Cart not found for customerId: {}", customerId);
                    return new ResourceNotFoundException("Cart not found for customerId: " + customerId);
                });

        VendorProduct product = vendorProductRepository.findById(request.getVendorProductId())
                .orElseThrow(() -> {
                    logger.error("Vendor product not found with id: {}", request.getVendorProductId());
                    return new ResourceNotFoundException(
                            "Vendor product not found with ID: " + request.getVendorProductId());
                });
        if (!product.getApprovalStatus().equals(ApprovalStatus.APPROVED)) {
            throw new IllegalArgumentException("Product is not approved for sale");
        }
        if (product.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException(
                    "Max quantity available is: " + product.getStockQuantity() + ". Please Order less than that.");
        }
        CartItem cartItem = new CartItem(null, product, request.getQuantity());
        // checking if same item exists in cart already, if so just update quantity
        Optional<CartItem> vp = cart.getCartItems().stream()
                .filter(x -> x.getVendorProduct().getId().equals(product.getId())).findFirst();
        if (vp.isPresent()) {
            CartItem existingCartItem = vp.get();
            // try to update quantity
            int quantity = existingCartItem.getQuantity() + request.getQuantity();
            if (quantity > existingCartItem.getVendorProduct().getStockQuantity()) {
                throw new IllegalArgumentException(
                        "Max quantity available is: " + product.getStockQuantity() + ". Please Order less than that.");
            } else {
                existingCartItem.setQuantity(quantity);
                logger.info("Updated cart item for product '{}' (ID: {}) for customerId: {}. New total quantity: {}.",
                        product.getName(), product.getId(), customerId, existingCartItem.getQuantity());
                return new CartItemResponse(cartItemRepository.save(existingCartItem));
            }
        }
        cart.getCartItems().add(cartItem);
        shoppingCartRepository.save(cart);
        cartItemRepository.save(cartItem);
        logger.info("Added product '{}' (ID: {}) to cart for customerId: {} with quantity: {}.",
                product.getName(), product.getId(), customerId, cartItem.getQuantity());
        return new CartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(String cartItemId, UpdateCartItemRequest request) {
        logger.info("Updating quantity for cartItemId: {} to {}", cartItemId, request.getQuantity());
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    logger.error("Cart item not found with id: {}", cartItemId);
                    return new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
                });
        VendorProduct vendorProduct = cartItem.getVendorProduct();
        if (vendorProduct.getStockQuantity() < request.getQuantity()) {
            throw new IllegalArgumentException("Quantity is greater than stock available");
        }
        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        logger.info("Updated quantity for cartItemId: {} to {}", cartItemId, request.getQuantity());
        return new CartItemResponse(cartItem);
    }

    @Override
    public void removeCartItem(String cartItemId) {
        logger.info("Removing cart item with ID: {}", cartItemId);
        if (!cartItemRepository.existsById(cartItemId)) {
            logger.warn("Attempt to remove non-existing cart item with ID: {}", cartItemId);
            throw new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
        logger.info("Removed cart item with ID: {}", cartItemId);
    }

    @Override
    public void clearCart(String customerId) {
        logger.info("Clearing cart for customerId: {}", customerId);
        ShoppingCart cart = shoppingCartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> {
                    logger.error("Cart not found for customerId: {}", customerId);
                    return new ResourceNotFoundException("Cart not found for customerId: " + customerId);
                });
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart); // CascadeType.ALL ensures related items are deleted
    }
}
