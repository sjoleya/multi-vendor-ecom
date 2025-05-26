package com.vena.ecom.dto.response;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;

import java.util.List;

public class OrderResponse {
    private String orderId;
    private String customerId;
    private List<OrderItemResponse> items;
    private double totalAmount;
    private String status;
    private String createdAt;
    private String addressId;

    public OrderResponse() {
    }

    public OrderResponse(Order order) {
        User customer = order.getCustomer();
        Address address = order.getShippingAddress();

        this.orderId = order.getId();
        this.customerId = customer.getId();
        this.items = order.getOrderItems().stream()
                .map(OrderItemResponse::new)
                .toList();
        this.totalAmount = order.getTotalAmount().doubleValue();
        this.status = order.getOrderStatus().toString();
        this.createdAt = order.getOrderDate().toString();
        this.addressId = address.getId();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}
