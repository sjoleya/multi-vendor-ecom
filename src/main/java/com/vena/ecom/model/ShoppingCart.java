package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingCart extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_items", referencedColumnName = "id")
    private List<CartItem> cartItems = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(String id, User customer, List<CartItem> cartItems) {
        this.id = id;
        this.customer = customer;
        this.cartItems = cartItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id='" + id + '\'' +
                ", customer=" + customer +
                ", cartItems=" + cartItems +
                '}';
    }
}
