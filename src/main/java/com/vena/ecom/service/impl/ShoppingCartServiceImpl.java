package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddCartItemRequest;
import com.vena.ecom.dto.request.UpdateCartItemRequest;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.*;
import com.vena.ecom.repo.*;
import com.vena.ecom.service.ShoppingCartService;
import com.vena.ecom.dto.response.ShoppingCartResponse;
import com.vena.ecom.dto.response.CartItemResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
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
        return toShoppingCartResponse(cart);
    }

    @Override
    public CartItemResponse addCartItem(String customerId, AddCartItemRequest request) {
        logger.info("Adding product '{}' to cart for customerId: {}", request.getVendorProductId(), customerId);
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> {
                    logger.error("Cart not found for customerId: {}", customerId);
                    return new ResourceNotFoundException("Cart not found for customerId: " + customerId);
                });

        VendorProduct product = vendorProductRepository.findById(request.getVendorProductId())
                .orElseThrow(() -> {
                    logger.error("Vendor product not found with id: {}", request.getVendorProductId());
                    return new ResourceNotFoundException("Vendor product not found with ID: " + request.getVendorProductId());
                });

        if (request.getQuantity() <= 0) {
            logger.warn("Invalid quantity {} requested to add for product '{}'", request.getQuantity(), product.getName());
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        CartItem cartItem = new CartItem(null, product, request.getQuantity());
        cart.getCartItems().add(cartItem);
        shoppingCartRepository.save(cart);
        cartItemRepository.save(cartItem);
        logger.info("Added product '{}' to cart for customerId: {} with quantity: {}", product.getName(), customerId, request.getQuantity());
        return toCartItemResponse(cartItem);
    }

    @Override
    public CartItemResponse updateCartItemQuantity(String cartItemId, UpdateCartItemRequest request) {
        logger.info("Updating quantity for cartItemId: {} to {}", cartItemId, request.getQuantity());
        if (request.getQuantity() <= 0) {
            logger.warn("Invalid quantity {} for update on cartItemId: {}", request.getQuantity(), cartItemId);
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> {
                    logger.error("Cart item not found with id: {}", cartItemId);
                    return new ResourceNotFoundException("Cart item not found with ID: " + cartItemId);
                });

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);
        logger.info("Updated quantity for cartItemId: {} to {}", cartItemId, request.getQuantity());
        return toCartItemResponse(cartItem);
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
        ShoppingCart cart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> {
                    logger.error("Cart not found for customerId: {}", customerId);
                    return new ResourceNotFoundException("Cart not found for customerId: " + customerId);
                });
        cart.getCartItems().clear();
        shoppingCartRepository.save(cart);
        logger.info("Cart cleared successfully for customerId: {}", customerId);
    }

    private ShoppingCartResponse toShoppingCartResponse(ShoppingCart cart) {
        ShoppingCartResponse dto = new ShoppingCartResponse();
        dto.customerId = cart.getCustomer().getId();
        dto.items = cart.getCartItems().stream().map(this::toCartItemResponse).toList();
        dto.totalAmount = cart.getCartItems().stream()
                .mapToDouble(item -> item.getVendorProduct().getPrice().doubleValue() * item.getQuantity()).sum();
        return dto;
    }

    private CartItemResponse toCartItemResponse(CartItem item) {
        CartItemResponse dto = new CartItemResponse();
        dto.cartItemId = item.getId();
        dto.productId = item.getVendorProduct().getId();
        dto.productName = item.getVendorProduct().getName();
        dto.quantity = item.getQuantity();
        dto.price = item.getVendorProduct().getPrice().doubleValue();
        dto.totalPrice = dto.price * dto.quantity;
        return dto;
    }
}
