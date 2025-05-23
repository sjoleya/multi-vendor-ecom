package com.vena.ecom.dto.request;

public class CheckoutRequest {
    private String customerId;
    private String addressId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public CheckoutRequest() {}

    public CheckoutRequest(String customerId, String addressId) {
        this.customerId = customerId;
        this.addressId = addressId;
    }
}
