package com.vena.ecom.service;

import com.vena.ecom.dto.request.OrderPaymentRequest;
import com.vena.ecom.dto.response.OrderPaymentResponse;
import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.ReviewResponse;
import com.vena.ecom.model.Review;

import java.util.List;

public interface OrderService {
    OrderResponse checkout(String customerId, String addressId);

    List<OrderResponse> getOrderHistory(String customerId);

    OrderResponse getOrderDetails(String orderId);

    ReviewResponse submitProductReview(String orderId, String orderItemId, String customerId, Review review);

    OrderPaymentResponse submitOrderPayment(OrderPaymentRequest paymentRequest);
}
