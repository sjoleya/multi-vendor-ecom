package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.ApprovalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "vendor_profiles")
public class VendorProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private User vendor;
    private String storeName;
    private String storeDescription;

    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    public VendorProfile() {
    }

    public VendorProfile(String id, User vendor, String storeName,
            String storeDescription, String contactNumber,
            ApprovalStatus approvalStatus) {
        this.id = id;
        this.vendor = vendor;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.contactNumber = contactNumber;
        this.approvalStatus = approvalStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getVendor() {
        return vendor;
    }

    public void setVendor(User vendor) {
        this.vendor = vendor;
    }

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

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    @Override
    public String toString() {
        return "VendorProfile{" +
                "id='" + id + '\'' +
                ", Vendor=" + vendor +
                ", storeName='" + storeName + '\'' +
                ", storeDescription='" + storeDescription + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", approvalStatus=" + approvalStatus +
                '}';
    }
}
