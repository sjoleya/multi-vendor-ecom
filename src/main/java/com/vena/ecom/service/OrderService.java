package com.vena.ecom.service;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.Review;

import java.util.List;

public interface OrderService {
    //GET /vendor/orders – List vendor order items
    //GET /vendor/orders/items/{orderItemId} – Get order item details
    //PUT /vendor/orders/items/{orderItemId}/status – Update order item status

//    public List<Order> getAllOrder();
//    public
// CUSTOMER
//    Order checkout(String customerId);
//    List<Order> getOrderHistory(String customerId);
//    Order getOrderDetails(String orderId);
//    Review submitProductReview(String orderId, String orderItemId, String customerId, Review review);
//
//    // VENDOR
//    List<OrderItem> getVendorOrderItems(String vendorId);
//    OrderItem getOrderItemDetails(String orderItemId);
//    OrderItem updateOrderItemStatus(String orderItemId, OrderItemStatus status);

    Order checkout(String customerId);
    List<Order> getOrderHistory(String customerId);
    Order getOrderDetails(String orderId);
    Review submitProductReview(String orderId, String orderItemId, String customerId, Review review);

    //vendor
    List<OrderItem> getVendorOrderItems(String vendorId);
    OrderItem getOrderItemDetails(String orderItemId);
    OrderItem updateOrderItemStatus(String orderItemId, OrderItemStatus status);

}
