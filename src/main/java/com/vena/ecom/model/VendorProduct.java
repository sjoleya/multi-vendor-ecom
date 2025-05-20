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
    @JoinColumn(name = "catalog_Id",referencedColumnName = "catalogId")
    private ProductCatalog catalogPrductId;

    @ManyToOne
    @JoinColumn(name = "vendor_Id",referencedColumnName = "vendorId")
    private String vendorId;
    private String sku;
    private BigDecimal price;
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "shippingAddressId", referencedColumnName = "addressId")
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private  Boolean isActive;
    private  BigDecimal avgrageRating;

    public VendorProduct() {
    }

    public VendorProduct(ProductCatalog catalogPrductId, String vendorProductId, String vendorId, String sku, BigDecimal price, Integer stockQuantity, Address shippingAddress, ApprovalStatus approvalStatus, Boolean isActive, BigDecimal avgrageRating) {
        this.catalogPrductId = catalogPrductId;
        this.vendorProductId = vendorProductId;
        this.vendorId = vendorId;
        this.sku = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.shippingAddress = shippingAddress;
        this.approvalStatus = approvalStatus;
        this.isActive = isActive;
        this.avgrageRating = avgrageRating;
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public ProductCatalog getCatalogPrductId() {
        return catalogPrductId;
    }

    public void setCatalogPrductId(ProductCatalog catalogPrductId) {
        this.catalogPrductId = catalogPrductId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public BigDecimal getAvgrageRating() {
        return avgrageRating;
    }

    public void setAvgrageRating(BigDecimal avgrageRating) {
        this.avgrageRating = avgrageRating;
    }
}
