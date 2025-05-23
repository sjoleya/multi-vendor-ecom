package com.vena.ecom.dto.request;

import com.vena.ecom.model.enums.PaymentMethod;

public class OrderPaymentRequest {
    public String orderId;
    public PaymentMethod paymentMethod;
    public Double amount;
    public String transactionId;

    public OrderPaymentRequest(String orderId, PaymentMethod paymentMethod, Double amount, String transactionId) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public OrderPaymentRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
