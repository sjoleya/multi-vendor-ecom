package com.vena.ecom.service.impl;

import com.vena.ecom.model.enums.ItemStatus;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.dto.request.AddProductReview;
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
import com.vena.ecom.model.Payment;
import com.vena.ecom.model.enums.PaymentStatus;
import com.vena.ecom.service.OrderService;
import com.vena.ecom.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderResponse checkout(String customerId, String addressId) {
        logger.info("checkout - Checking out for customer ID: {} and address ID: {}", customerId, addressId);
        ShoppingCart shoppingCart = shoppingCartRepository.findByCustomerId(customerId).map(sc -> {
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
        if (!user.getAddressList().contains(address)) {
            throw new IllegalArgumentException("Address should belong only to user placing order.");
        }
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING_PAYMENT);
        order.setShippingAddress(address);
        logger.debug("checkout - New order created: {}", order);

        List<CartItem> cartItems = shoppingCart.getCartItems();
        BigDecimal totalAmount = BigDecimal.ZERO;
        // saving local copy of all vendor products, for quantity validation,
        // and reducing redundant db calls.
        Map<String, VendorProduct> productsToUpdate = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            logger.debug("checkout - Processing cart item: {}", cartItem);
            VendorProduct vendorProduct = cartItem.getVendorProduct();
            if (productsToUpdate.containsKey(vendorProduct.getId())) {
                vendorProduct = productsToUpdate.get(vendorProduct.getId());
            } else {
                productsToUpdate.put(vendorProduct.getId(), vendorProduct);
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVendorProduct(cartItem.getVendorProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            if (vendorProduct.getStockQuantity() < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Product Out of Stock.");
            }
            vendorProduct.setStockQuantity(vendorProduct.getStockQuantity() - cartItem.getQuantity());
            orderItem.setPriceAtPurchase(vendorProduct.getPrice());
            orderItem.setSubtotal(orderItem.getPriceAtPurchase().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItem.setItemStatus(ItemStatus.PENDING);
            productsToUpdate.put(vendorProduct.getId(), vendorProduct);
            logger.debug("checkout - Created order item: {}", orderItem);
            totalAmount = totalAmount.add(orderItem.getSubtotal());
            order.getOrderItems().add(orderItem);
        }
        vendorProductRepository.saveAll(productsToUpdate.values());

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
        List<OrderResponse> orderHistory = orderRepository.findByCustomerId(customerId)
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
    public ReviewResponse submitProductReview(String orderId, String orderItemId,
            AddProductReview addProductReview) {
        logger.info("Submitting product review for order ID: {}, order item ID: {}, rating: {}, comment: {}", orderId,
                orderItemId, addProductReview.getRating(), addProductReview.getComment());
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
        if (!orderItem.getItemStatus().equals(ItemStatus.DELIVERED)) {
            throw new IllegalArgumentException("Review for this Order Item is not allowed");
        }
        if (!orderItem.getOrder().getId().equals(order.getId())) {
            throw new IllegalArgumentException("Order Item doesn't belong to this Order");
        }
        // Calculating Average Rating from new review
        VendorProduct vendorProduct = orderItem.getVendorProduct();
        long currentCount = reviewRepository.countByOrderItem_VendorProduct(vendorProduct);
        BigDecimal currentAverage = vendorProduct.getAverageRating();
        BigDecimal newTotal = currentAverage.multiply(BigDecimal.valueOf(currentCount))
                .add(BigDecimal.valueOf(addProductReview.getRating()));

        long newCount = currentCount + 1L;

        BigDecimal newAverage = newTotal.divide(BigDecimal.valueOf(newCount), 2,
                RoundingMode.HALF_UP);
        vendorProduct.setAverageRating(newAverage);
        logger.info("Saving Updated Vendor Product: {} into DB", vendorProduct.getId());
        vendorProductRepository.save(vendorProduct);
        Review review = new Review();
        review.setRating(addProductReview.getRating());
        review.setComment(addProductReview.getComment());
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
        // validating payment details
        BigDecimal orderAmount = order.getTotalAmount(); // Already a BigDecimal
        Double paymentAmount = paymentRequest.getAmount(); // This is a Double
        BigDecimal paymentAmountBD = BigDecimal.valueOf(paymentAmount);
        if (orderAmount.compareTo(paymentAmountBD) != 0) {
            throw new IllegalArgumentException("Payment Amount should match order amount.");
        }
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
