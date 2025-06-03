package com.vena.ecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vena.ecom.dto.request.CheckoutRequest;
import com.vena.ecom.dto.request.OrderPaymentRequest;
import com.vena.ecom.dto.response.OrderPaymentResponse;
import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.request.AddProductReview;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        logger.info("POST /customers/orders - Checkout request received for customer ID: {} and address ID: {}",
                checkoutRequest.getCustomerId(),
                checkoutRequest.getAddressId());
        OrderResponse order = orderService.checkout(checkoutRequest.getCustomerId(), checkoutRequest.getAddressId());
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestParam String customerId) {
        logger.info("GET /customers/orders - Fetching order history for customer ID: {}", customerId);
        List<OrderResponse> orders = orderService.getOrderHistory(customerId);
        logger.info("Found {} orders for customer ID: {}", orders.size(), customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable String orderId) {
        logger.info("GET /customers/orders/{} - Fetching order details", orderId);
        OrderResponse order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/items/{orderItemId}/review")
    public ResponseEntity<ReviewResponse> submitProductReview(@PathVariable String orderId,
            @PathVariable String orderItemId,
            @RequestBody AddProductReview addProductReview) {
        logger.info(
                "POST /customers/orders/{}/items/{}/review - Submitting product review for order ID: {}, order item ID: {}, rating: {}, comment: {}",
                orderId,
                orderItemId, addProductReview.getRating(), addProductReview.getComment());
        ReviewResponse submittedReview = orderService.submitProductReview(orderId, orderItemId, addProductReview);
        return ResponseEntity.ok(submittedReview);
    }

    @PutMapping("/payment")
    public ResponseEntity<OrderPaymentResponse> submitOrderPayment(@RequestBody OrderPaymentRequest paymentRequest) {
        logger.info("PUT /customers/orders/payment - Submitting order payment for order ID: {}",
                paymentRequest.getOrderId());
        return new ResponseEntity<>(orderService.submitOrderPayment(paymentRequest), HttpStatus.CREATED);
    }
}