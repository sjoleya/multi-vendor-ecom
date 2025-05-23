package com.vena.ecom.dto.response;

public class OrderResponse {
    public String id;
    public String customerId;
    public java.util.List<OrderItemResponse> items;
    public double totalAmount;
    public String status;
    public String createdAt;
    public String addressId;
}
