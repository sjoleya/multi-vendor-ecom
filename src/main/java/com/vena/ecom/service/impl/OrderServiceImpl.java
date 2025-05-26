package com.vena.ecom.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.dto.request.OrderPaymentRequest;
import com.vena.ecom.dto.response.OrderItemResponse;
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
        logger.info("checkout - Checking out for customer ID: {} and address ID: {}", customerId, addressId);
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomer_Id(customerId).map(sc -> {
            logger.debug("checkout - Shopping cart found: {}", sc);
            return sc;
        }).orElseThrow(() -> {
            logger.warn("checkout - Shopping cart not found for customer ID: {}", customerId);
            return new ResourceNotFoundException("Shopping cart not found");
        });

        User user = userRepository.findById(customerId).orElseThrow(() -> {
            logger.warn("checkout - User not found with id: {}", customerId);
            return new ResourceNotFoundException("User with id: " + customerId + "not found!");
        });
        logger.debug("checkout - User found: {}", user);

        Address address = userAddressRepository.findById(addressId).map(a -> {
            logger.debug("checkout - Address found: {}", a);
            return a;
        }).orElseThrow(() -> {
            logger.warn("checkout - Address not found with id: {}", addressId);
            return new ResourceNotFoundException("Address with id: " + addressId + "not found!");
        });
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(com.vena.ecom.model.enums.OrderStatus.PENDING_PAYMENT);
        order.setShippingAddress(address);
        logger.debug("checkout - New order created: {}", order);

        List<CartItem> cartItems = shoppingCart.getCartItems();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            logger.debug("checkout - Processing cart item: {}", cartItem);
            VendorProduct vendorProduct = cartItem.getVendorProduct();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVendorProduct(cartItem.getVendorProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(vendorProduct.getPrice());
            orderItem.setSubtotal(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setItemStatus(com.vena.ecom.model.enums.ItemStatus.PENDING);
            logger.debug("checkout - Created order item: {}", orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);
        logger.debug("checkout - Order saved: {}", savedOrder);

        shoppingCartService.clearCart(customerId);
        logger.info("checkout - Order created with ID: {}", savedOrder.getId());
        try {
            OrderResponse orderResponse = new OrderResponse(savedOrder);
            logger.debug("checkout - Converted order to response: {}", orderResponse);
            return orderResponse;
        } catch (Exception e) {
            logger.error("checkout - Error while converting order to response for order id: {}", savedOrder.getId(), e);
            throw e;
        }
    }

    @Override
    public List<OrderResponse> getOrderHistory(String customerId) {
        logger.info("getOrderHistory - Fetching order history for customer ID: {}", customerId);
        List<OrderResponse> orderHistory = orderRepository.findByCustomer_Id(customerId)
                .stream().map(OrderResponse::new).toList();
        logger.debug("getOrderHistory - Found {} orders for customer ID: {}", orderHistory.size(), customerId);
        return orderHistory;
    }

    @Override
    public OrderResponse getOrderDetails(String orderId) {
        logger.info("Fetching order details for order ID: {}", orderId);
        Order order = orderRepository.findById(orderId).map(o -> {
            logger.debug("getOrderDetails - Order found: {}", o);
            return o;
        }).orElseThrow(() -> {
            logger.warn("getOrderDetails - Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order not found");
        });
        try {
            OrderResponse orderResponse = new OrderResponse(order);
            logger.debug("getOrderDetails - Converted order to response: {}", orderResponse);
            return orderResponse;
        } catch (Exception e) {
            logger.error("getOrderDetails - Error while converting order to response for order id: {}", orderId, e);
            throw e;
        }
    }

    @Override
    public ReviewResponse submitProductReview(String orderId, String orderItemId, String customerId, Review review) {
        logger.info("Submitting product review for order ID: {}, order item ID: {}, customer ID: {}", orderId,
                orderItemId, customerId);
        Order order = orderRepository.findById(orderId).map(o -> {
            logger.debug("submitProductReview - Order found: {}", o);
            return o;
        }).orElseThrow(() -> {
            logger.warn("submitProductReview - Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order not found");
        });
        OrderItem orderItem = orderItemRepository.findById(orderItemId).map(oi -> {
            logger.debug("submitProductReview - Order item found: {}", oi);
            return oi;
        }).orElseThrow(() -> {
            logger.warn("submitProductReview - Order item not found with id: {}", orderItemId);
            return new ResourceNotFoundException("Order item not found");
        });
        if (!order.getCustomer().getId().equals(customerId)) {
            logger.warn("submitProductReview - Customer ID {} does not match order's customer ID {}", customerId,
                    order.getCustomer().getId());
            throw new ResourceNotFoundException("Customer mismatch");
        }

        review.setOrder(order);
        review.setOrderItem(orderItem);
        review.setCustomer(order.getCustomer());
        review.setOrder(order);
        review.setOrderItem(orderItem);
        review.setCustomer(order.getCustomer());
        return new ReviewResponse(reviewRepository.save(review));
    }

    @Override
    public OrderPaymentResponse submitOrderPayment(OrderPaymentRequest paymentRequest) {
        logger.info("Submitting order payment for order ID: {}", paymentRequest.getOrderId());
        Order order = orderRepository.findById(paymentRequest.getOrderId()).map(o -> {
            logger.debug("submitOrderPayment - Order found: {}", o);
            return o;
        }).orElseThrow(
                () -> {
                    logger.warn("submitOrderPayment - Order not found with id: {}", paymentRequest.getOrderId());
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
        logger.debug("submitOrderPayment - Payment created: {}", payment);

        order.setOrderStatus(com.vena.ecom.model.enums.OrderStatus.PROCESSING);

        paymentRepository.save(payment);
        logger.debug("submitOrderPayment - Payment saved: {}", payment);
        try {
            orderRepository.save(order);
            logger.debug("submitOrderPayment - Order status updated to PROCESSING for order ID: {}", order.getId());
            OrderPaymentResponse orderPaymentResponse = new OrderPaymentResponse(order.getId(), PaymentStatus.SUCCEEDED,
                    paymentRequest.getTransactionId(),
                    "Payment Successful", LocalDateTime.now());
            logger.debug("submitOrderPayment - Order payment response created: {}", orderPaymentResponse);
            return orderPaymentResponse;
        } catch (Exception e) {
            logger.error("submitOrderPayment - Error while creating order payment response for order id: {}",
                    order.getId(), e);
            throw e;
        }
    }
}
