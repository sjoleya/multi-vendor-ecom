package com.vena.ecom.dto.request;

import jakarta.validation.constraints.NotBlank;

public class UpdateVendorProfileRequest {
    @NotBlank(message = "Store name is required")
    private String storeName;
    @NotBlank(message = "Store description is required")
    private String storeDescription;
    private String contactNumber;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}