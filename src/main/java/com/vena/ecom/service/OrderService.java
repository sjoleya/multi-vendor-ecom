package com.vena.ecom.service;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.Review;

import java.util.List;

public interface OrderService {
    Order checkout(String customerId);

    List<Order> getOrderHistory(String customerId);

    Order getOrderDetails(String orderId);

    Review submitProductReview(String orderId, String orderItemId, String customerId, Review review);

}
