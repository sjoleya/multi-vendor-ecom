package com.vena.ecom.dto.response;

import com.vena.ecom.model.CartItem;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.model.VendorProduct;

public class CartItemResponse {
    private String cartItemId;
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;

    public CartItemResponse() {
    }

    public CartItemResponse(CartItem cartItem) {
        VendorProduct vendorProduct = cartItem.getVendorProduct();
        ProductCatalog productCatalog = vendorProduct.getProductCatalog();

        this.cartItemId = cartItem.getId();
        this.productId = productCatalog.getId();
        this.productName = productCatalog.getName();
        this.quantity = cartItem.getQuantity();
        this.price = vendorProduct.getPrice().doubleValue();
        this.totalPrice = cartItem.getQuantity() * vendorProduct.getPrice().doubleValue();
    }

    public String getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(String cartItemId) {
        this.cartItemId = cartItemId;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
