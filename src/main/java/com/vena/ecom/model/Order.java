package com.vena.ecom.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.OrderStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "userId")
    private User customer;

    private LocalDateTime orderDate;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus overallOrderStatus;

    @ManyToOne
    @JoinColumn(name = "shippingAddressId", referencedColumnName = "addressId")
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "billingAddress", referencedColumnName = "addressId")
    private Address billingAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    public Order() {
    }

    public Order(String orderId, User customer, LocalDateTime orderDate, BigDecimal totalAmount,
            OrderStatus overallOrderStatus, Address shippingAddress, Address billingAddress, List<OrderItem> orderItems,
            Payment payment) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.overallOrderStatus = overallOrderStatus;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.orderItems = orderItems;
        this.payment = payment;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOverallOrderStatus() {
        return overallOrderStatus;
    }

    public void setOverallOrderStatus(OrderStatus overallOrderStatus) {
        this.overallOrderStatus = overallOrderStatus;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
