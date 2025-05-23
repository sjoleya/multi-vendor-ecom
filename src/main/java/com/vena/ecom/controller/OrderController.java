package com.vena.ecom.controller;

import com.vena.ecom.dto.request.CheckoutRequest;
import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.ReviewResponse;
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
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        OrderResponse order = orderService.checkout(checkoutRequest.getCustomerId(), checkoutRequest.getAddressId());
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrderHistory(@RequestParam String customerId) {
        List<OrderResponse> orders = orderService.getOrderHistory(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable String orderId) {
        OrderResponse order = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{orderId}/items/{orderItemId}/review")
    public ResponseEntity<ReviewResponse> submitProductReview(@PathVariable String orderId,
            @PathVariable String orderItemId,
            @PathVariable String customerId, @RequestBody Review review) {
        ReviewResponse submittedReview = orderService.submitProductReview(orderId, orderItemId, customerId, review);
        return ResponseEntity.ok(submittedReview);
    }
}