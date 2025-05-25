package com.vena.ecom.dto.response;

import com.vena.ecom.model.VendorProfile;

public class VendorProfileResponse {
    private String vendorProfileId;
    private String userId;
    private String storeName;
    private String status;

    public VendorProfileResponse() {
    }

    public VendorProfileResponse(VendorProfile vendorProfile) {
        this.vendorProfileId = vendorProfile.getId();
        this.userId = vendorProfile.getUser().getId();
        this.storeName = vendorProfile.getStoreName();
        this.status = vendorProfile.getApprovalStatus().toString();
    }

    public String getVendorProfileId() {
        return vendorProfileId;
    }

    public void setVendorProfileId(String vendorProfileId) {
        this.vendorProfileId = vendorProfileId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
