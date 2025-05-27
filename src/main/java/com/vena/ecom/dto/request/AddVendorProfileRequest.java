package com.vena.ecom.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddVendorProfileRequest {
    @NotBlank(message = "User ID cannot be blank")
    private String userId;
    @NotBlank(message = "Shop name cannot be blank")
    @Size(max = 255, message = "Shop name cannot be longer than 255 characters")
    private String shopName;
    @Size(max = 1000, message = "Description cannot be longer than 1000 characters")
    private String description;
    @NotBlank(message = "Contact number cannot be blank")
    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 digits")
    private String contactNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
