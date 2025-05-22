package com.vena.ecom.controller;

import com.vena.ecom.dto.CheckoutRequest;
import com.vena.ecom.model.Order;
import com.vena.ecom.model.Review;
import com.vena.ecom.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        Order order = orderService.checkout(checkoutRequest.getCustomerId(), checkoutRequest.getAddressId());
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrderHistory(@RequestParam String customerId) {
        List<Order> orders = orderService.getOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable String orderId) {
        Order order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/items/{orderItemId}/review")
    public ResponseEntity<Review> submitProductReview(@PathVariable String orderId, @PathVariable String orderItemId,
            @PathVariable String customerId, @RequestBody Review review) {
        Review submittedReview = orderService.submitProductReview(orderId, orderItemId, customerId, review);
        return ResponseEntity.ok(submittedReview);
    }
}