package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

@Entity
public class CartItem extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "vendor_product_id", referencedColumnName = "id")
    private VendorProduct vendorProduct;

    private Integer quantity;

    public CartItem() {
    }

    public CartItem(String id, ShoppingCart cart, VendorProduct vendorProduct, Integer quantity) {
        this.id = id;
        this.cart = cart;
        this.vendorProduct = vendorProduct;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        this.quantity = quantity;
    }
}
