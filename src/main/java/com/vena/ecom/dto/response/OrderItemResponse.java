package com.vena.ecom.dto.response;

import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.model.VendorProduct;

public class OrderItemResponse {
    private String orderItemId;
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private String status;
    private String vendorId;

    public OrderItemResponse() {
    }

    public OrderItemResponse(OrderItem orderItem) {
        VendorProduct vendorProduct = orderItem.getVendorProduct();
        ProductCatalog productCatalog = vendorProduct.getProductCatalog();

        this.orderItemId = orderItem.getId();
        this.productId = productCatalog.getId();
        this.productName = productCatalog.getName();
        this.quantity = orderItem.getQuantity();
        this.price = vendorProduct.getPrice().doubleValue();
        this.status = orderItem.getItemStatus().toString();
        this.vendorId = vendorProduct.getVendorProfile().getId();
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
