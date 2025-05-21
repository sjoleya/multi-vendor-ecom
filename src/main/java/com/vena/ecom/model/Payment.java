package com.vena.ecom.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.PaymentStatus;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String paymentId;

    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "orderId")
    private Order order;

    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String transactionId; // Simulated

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    public Payment() {
    }

    public Payment(String paymentId, Order order, LocalDateTime paymentDate, BigDecimal amount, String paymentMethod,
            String transactionId, PaymentStatus status) {
        this.paymentId = paymentId;
        this.order = order;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
