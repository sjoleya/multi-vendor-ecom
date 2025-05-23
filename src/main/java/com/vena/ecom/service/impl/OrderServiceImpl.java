package com.vena.ecom.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.dto.request.OrderPaymentRequest;
import com.vena.ecom.dto.response.OrderPaymentResponse;
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
import com.vena.ecom.model.Payment;
import com.vena.ecom.model.enums.PaymentStatus;
import com.vena.ecom.repo.AddressRepository;
import com.vena.ecom.repo.PaymentRepository;
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

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

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

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public OrderResponse checkout(String customerId, String addressId) {
        logger.info("Checking out for customer ID: {} and address ID: {}", customerId, addressId);
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer_Id(customerId).map(sc -> {
            logger.info("Shopping cart found for customer ID: {}", customerId);
            return sc;
        }).orElseThrow(() -> {
            logger.warn("Shopping cart not found for customer ID: {}", customerId);
            return new ResourceNotFoundException("Shopping cart not found");
        });

        User user = userRepository.findById(customerId).get();
        Address address = userAddressRepository.findById(addressId).map(a -> {
            logger.info("Address found with id: {}", addressId);
            return a;
        }).orElseThrow(() -> {
            logger.warn("Address not found with id: {}", addressId);
            return new ResourceNotFoundException("Address with id: " + addressId + "not found!");
        });
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

            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        shoppingCartService.clearCart(customerId);
        logger.info("Order created with ID: {}", savedOrder.getId());
        try {
            return toOrderResponse(savedOrder);
        } catch (Exception e) {
            logger.error("Error while converting order to response for order id: {}", savedOrder.getId(), e);
            throw e;
        }
    }

    @Override
    public List<OrderResponse> getOrderHistory(String customerId) {
        logger.info("Fetching order history for customer ID: {}", customerId);
        return orderRepository.findByCustomer_Id(customerId)
                .stream().map(this::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderDetails(String orderId) {
        logger.info("Fetching order details for order ID: {}", orderId);
        Order order = orderRepository.findById(orderId).map(o -> {
            logger.info("Order found with id: {}", orderId);
            return o;
        }).orElseThrow(() -> {
            logger.warn("Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order not found");
        });
        try {
            return toOrderResponse(order);
        } catch (Exception e) {
            logger.error("Error while converting order to response for order id: {}", orderId, e);
            throw e;
        }
    }

    @Override
    public ReviewResponse submitProductReview(String orderId, String orderItemId, String customerId, Review review) {
        logger.info("Submitting product review for order ID: {}, order item ID: {}, customer ID: {}", orderId,
                orderItemId, customerId);
        Order order = orderRepository.findById(orderId).map(o -> {
            logger.info("Order found with id: {}", orderId);
            return o;
        }).orElseThrow(() -> {
            logger.warn("Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order not found");
        });
        OrderItem orderItem = orderItemRepository.findById(orderItemId).map(oi -> {
            logger.info("Order item found with id: {}", orderItemId);
            return oi;
        }).orElseThrow(() -> {
            logger.warn("Order item not found with id: {}", orderItemId);
            return new ResourceNotFoundException("Order item not found");
        });
        if (!order.getCustomer().getId().equals(customerId)) {
            logger.warn("Customer ID {} does not match order's customer ID {}", customerId,
                    order.getCustomer().getId());
            throw new ResourceNotFoundException("Customer mismatch");
        }

        review.setOrder(order);
        review.setOrderItem(orderItem);
        review.setCustomer(order.getCustomer());
        Review savedReview = reviewRepository.save(review);
        try {
            return toReviewResponse(savedReview);
        } catch (Exception e) {
            logger.error("Error while converting review to response for review id: {}", savedReview.getId(), e);
            throw e;
        }
    }

    private OrderResponse toOrderResponse(Order order) {
        OrderResponse dto = new OrderResponse();
        dto.orderId = order.getId();
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
        dto.orderItemId = item.getId();
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
        dto.reviewId = review.getId();
        dto.orderItemId = review.getOrderItem() != null ? review.getOrderItem().getId() : null;
        dto.customerId = review.getCustomer() != null ? review.getCustomer().getId() : null;
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        dto.createdAt = review.getCreatedAt() != null ? review.getCreatedAt().toString() : null;
        return dto;
    }

    @Override
    public OrderPaymentResponse submitOrderPayment(OrderPaymentRequest paymentRequest) {
        logger.info("Submitting order payment for order ID: {}", paymentRequest.getOrderId());
        Order order = orderRepository.findById(paymentRequest.getOrderId()).map(o -> {
            logger.info("Order found with id: {}", paymentRequest.getOrderId());
            return o;
        }).orElseThrow(
                () -> {
                    logger.warn("Order not found with id: {}", paymentRequest.getOrderId());
                    return new ResourceNotFoundException("Order not found with id: " + paymentRequest.getOrderId());
                });

        if (order.getOrderStatus() != com.vena.ecom.model.enums.OrderStatus.PENDING_PAYMENT) {
            logger.warn("Order {} is not in PENDING_PAYMENT status", paymentRequest.getOrderId());
            throw new IllegalStateException("Order is not in PENDING_PAYMENT status.");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setAmount(order.getTotalAmount());
        payment.setTransactionId(paymentRequest.getTransactionId());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCEEDED);

        order.setOrderStatus(com.vena.ecom.model.enums.OrderStatus.PROCESSING);

        paymentRepository.save(payment);
        try {
            orderRepository.save(order);
            return new OrderPaymentResponse(order.getId(), PaymentStatus.SUCCEEDED, paymentRequest.getTransactionId(),
                    "Payment Successful", LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Error while creating order payment response for order id: {}", order.getId(), e);
            throw e;
        }
    }
}
