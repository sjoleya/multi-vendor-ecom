package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.ApprovalStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "vendor_product")
public class VendorProduct extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private ProductCatalog catalogProductId;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorProfile vendorId;
    private String SKU;
    private BigDecimal price;
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private Boolean isActive;
    private BigDecimal averageRating;

    private String name;
    private String description;

    public VendorProduct() {
    }

    public VendorProduct(ProductCatalog catalogProductId, String id, VendorProfile vendorId, String sku,
            BigDecimal price, Integer stockQuantity, ApprovalStatus approvalStatus,
            Boolean isActive, BigDecimal averageRating) {
        this.catalogProductId = catalogProductId;
        this.id = id;
        this.vendorId = vendorId;
        this.SKU = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.approvalStatus = approvalStatus;
        this.isActive = isActive;
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
