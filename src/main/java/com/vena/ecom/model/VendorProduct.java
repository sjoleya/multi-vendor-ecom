package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.ApprovalStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Table(name = "vendor_product")
public class VendorProduct extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String vendorProductId;

    @ManyToOne
    @JoinColumn(name = "catalog_Id", referencedColumnName = "catalogId")
    private ProductCatalog catalogProductId;

    @ManyToOne
    @JoinColumn(name = "vendor_Id", referencedColumnName = "vendorId")
    private VendorProfile vendorId;
    private String SKU;
    private BigDecimal price;
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "shippingAddressId", referencedColumnName = "addressId")
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private Boolean isActive;
    private BigDecimal averageRating;

    public VendorProduct() {
    }

    public VendorProduct(ProductCatalog catalogProductId, String vendorProductId, VendorProfile vendorId, String sku,
            BigDecimal price, Integer stockQuantity, Address shippingAddress, ApprovalStatus approvalStatus,
            Boolean isActive, BigDecimal averageRating) {
        this.catalogProductId = catalogProductId;
        this.vendorProductId = vendorProductId;
        this.vendorId = vendorId;
        this.SKU = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.shippingAddress = shippingAddress;
        this.approvalStatus = approvalStatus;
        this.isActive = isActive;
        this.averageRating = averageRating;
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public ProductCatalog getCatalogProductId() {
        return catalogProductId;
    }

    public void setCatalogProductId(ProductCatalog catalogProductId) {
        this.catalogProductId = catalogProductId;
    }

    public VendorProfile getVendorId() {
        return vendorId;
    }

    public void setVendorId(VendorProfile vendorId) {
        this.vendorId = vendorId;
    }

    public String getSku() {
        return SKU;
    }

    public void setSku(String sku) {
        this.SKU = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }
}
