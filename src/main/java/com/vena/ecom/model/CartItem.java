package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

@Entity
public class CartItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String CartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "vendorProductId", referencedColumnName = "vendorProductId")
    private VendorProduct vendorProduct;

    private Integer quantity;

    public CartItem() {
    }

    public CartItem(String cartItemId, ShoppingCart cart, VendorProduct vendorProduct, Integer quantity) {
        CartItemId = cartItemId;
        this.cart = cart;
        this.vendorProduct = vendorProduct;
        quantity = quantity;
    }

    public String getCartItemId() {
        return CartItemId;
    }

    public void setCartItemId(String cartItemId) {
        CartItemId = cartItemId;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public VendorProduct getVendorProduct() {
        return vendorProduct;
    }

    public void setVendorProduct(VendorProduct vendorProduct) {
        this.vendorProduct = vendorProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        quantity = quantity;
    }

    public void setProduct(VendorProduct product) {
    }
}
