package com.vena.ecom.model;


import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.ApprovalStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "vendor_profiles")
public class VendorProfile extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vendorId;

    @OneToOne
    @JoinColumn(name = "userId" , referencedColumnName = "userId")
    private User user;
    private String storeName;
    private String storeDescription;
    private Address businessAddress;
    private String contactNumber;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;


    public VendorProfile() {
    }


    public VendorProfile(String vendorId, User user, String storeName,
                         String storeDescription, Address businessAddress, String contactNumber,
                         ApprovalStatus approvalStatus) {
        this.vendorId = vendorId;
        this.user = user;
        this.storeName = storeName;
        this.storeDescription = storeDescription;
        this.businessAddress = businessAddress;
        this.contactNumber = contactNumber;
        this.approvalStatus = approvalStatus;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Address getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(Address businessAddress) {
        this.businessAddress = businessAddress;
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

}

