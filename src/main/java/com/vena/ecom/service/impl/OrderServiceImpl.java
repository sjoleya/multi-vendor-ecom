package com.vena.ecom.service.impl;

import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.Order;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.Review;
import com.vena.ecom.model.ShoppingCart;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.OrderRepository;
import com.vena.ecom.repo.ReviewRepository;
import com.vena.ecom.repo.ShoppingCartRepository;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.OrderService;
import com.vena.ecom.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository userAddressRepository;

    @Override
    public OrderResponse checkout(String customerId, String addressId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer_Id(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping cart not found"));

        User user = userRepository.findById(customerId).get();
        Address address = userAddressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id: " + addressId + "not found!"));
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(com.vena.ecom.model.enums.OrderStatus.PENDING_PAYMENT);
        order.setShippingAddress(address);

        List<CartItem> cartItems = shoppingCart.getCartItems();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            VendorProduct vendorProduct = cartItem.getVendorProduct();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVendorProduct(cartItem.getVendorProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(vendorProduct.getPrice());
            orderItem.setSubtotal(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setItemStatus(com.vena.ecom.model.enums.ItemStatus.PENDING);
            orderItemRepository.save(orderItem);

            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        shoppingCartService.clearCart(customerId);
        return toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrderHistory(String customerId) {
        return orderRepository.findByCustomer_Id(customerId)
                .stream().map(this::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderDetails(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return toOrderResponse(order);
    }

    @Override
    public ReviewResponse submitProductReview(String orderId, String orderItemId, String customerId, Review review) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new ResourceNotFoundException("Customer mismatch");
        }
        review.setOrder(order);
        review.setOrderItem(orderItem);
        review.setCustomer(order.getCustomer());
        Review savedReview = reviewRepository.save(review);
        return toReviewResponse(savedReview);
    }

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.id = order.getId();
        dto.customerId = order.getCustomer().getId();
        dto.items = order.getOrderItems() != null
                ? order.getOrderItems().stream().map(this::toOrderItemResponse).toList()
                : java.util.Collections.emptyList();
        dto.totalAmount = order.getTotalAmount() != null ? order.getTotalAmount().doubleValue() : 0.0;
        dto.status = order.getOrderStatus() != null ? order.getOrderStatus().name() : null;
        dto.createdAt = order.getOrderDate() != null ? order.getOrderDate().toString() : null;
        dto.addressId = order.getShippingAddress() != null ? order.getShippingAddress().getId() : null;
        return dto;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.id = item.getId();
        dto.productId = item.getVendorProduct() != null ? item.getVendorProduct().getId() : null;
        dto.productName = item.getVendorProduct() != null ? item.getVendorProduct().getName() : null;
        dto.quantity = item.getQuantity() != null ? item.getQuantity() : 0;
        dto.price = item.getPriceAtPurchase() != null ? item.getPriceAtPurchase().doubleValue() : 0.0;
        dto.status = item.getItemStatus() != null ? item.getItemStatus().name() : null;
        dto.vendorId = item.getVendorProduct() != null && item.getVendorProduct().getVendorId() != null
                ? item.getVendorProduct().getVendorId().getId()
                : null;
        return dto;
    }

    private ReviewResponse toReviewResponse(Review review) {
        ReviewResponse dto = new ReviewResponse();
        dto.id = review.getId();
        dto.orderItemId = review.getOrderItem() != null ? review.getOrderItem().getId() : null;
        dto.customerId = review.getCustomer() != null ? review.getCustomer().getId() : null;
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        dto.createdAt = review.getCreatedAt() != null ? review.getCreatedAt().toString() : null;
        return dto;
    }

}
