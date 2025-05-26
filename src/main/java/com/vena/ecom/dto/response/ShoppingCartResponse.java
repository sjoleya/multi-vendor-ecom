package com.vena.ecom.dto.response;

import com.vena.ecom.model.ShoppingCart;
import com.vena.ecom.model.User;

import java.util.List;

public class ShoppingCartResponse {
    private String customerId;
    private List<CartItemResponse> items;
    private double totalAmount;

    public ShoppingCartResponse() {
    }

    public ShoppingCartResponse(ShoppingCart shoppingCart) {
        User customer = shoppingCart.getCustomer();

        this.customerId = customer.getId();
        this.items = shoppingCart.getCartItems().stream()
                .map(CartItemResponse::new)
                .toList();
        this.totalAmount = shoppingCart.getCartItems().stream()
                .mapToDouble(item -> item.getVendorProduct().getPrice().doubleValue())
                .sum();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<CartItemResponse> getItems() {
        return items;
    }

    public void setItems(List<CartItemResponse> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
