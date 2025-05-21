package com.vena.ecom.service.impl;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.Review;
import com.vena.ecom.model.User;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.OrderRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

//    @Autowired
//    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Order checkout(String customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(()-> new RuntimeException("Customer not found "));

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setOverallOrderStatus(OrderStatus.PROCESSING);
        order.setTotalAmount(new java.math.BigDecimal(00));
        return null;
    }

    @Override
    public List<Order> getOrderHistory(String customerId) {
        return orderRepository.findByCustomer_UserId(customerId);
    }

    @Override
    public Order getOrderDetails(String orderId) {
        return orderRepository.findById(orderId).
                orElseThrow(()->new RuntimeException("Order not found"));
    }

    @Override
    public Review submitProductReview(String orderId, String orderItemId, String customerId, Review review) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
        OrderItem item = orderItemRepository.findById(orderItemId).orElseThrow(()-> new RuntimeException("Order item not found"));
        if(!order.getCustomer().getUserId().equals(customerId))
        {
            throw new RuntimeException("Customer mismatch");
        }

        review.setOrder(order);
        review.setOrderItem(customerId);
        review.setCustomer(order.getCustomer());
        return null;
    }

    @Override
    public List<OrderItem> getVendorOrderItems(String vendorId) {
        return List.of();
    }

    @Override
    public OrderItem getOrderItemDetails(String orderItemId) {
        return null;
    }

    @Override
    public OrderItem updateOrderItemStatus(String orderItemId, OrderItemStatus status) {
        return null;
    }
}
