package com.vena.ecom.dto.response;

import java.time.LocalDateTime;

import com.vena.ecom.model.enums.PaymentStatus;

public class OrderPaymentResponse {
    public String orderId;
    public PaymentStatus paymentStatus;
    public String transactionId;
    public String message;
    public LocalDateTime timestamp;

    public OrderPaymentResponse(String orderId, PaymentStatus paymentStatus, String transactionId, String message, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public OrderPaymentResponse() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
