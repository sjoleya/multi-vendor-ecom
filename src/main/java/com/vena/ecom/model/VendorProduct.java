package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.ApprovalStatus;
import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

import java.math.BigDecimal;

@Entity
@Table(name = "vendor_product")
public class VendorProduct extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "catalog_id", referencedColumnName = "id")
    private ProductCatalog productCatalog;

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private VendorProfile vendorProfile;
    private String SKU;
    private BigDecimal price;
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;
    private Boolean isActive;
    private BigDecimal averageRating;

    private String name;
    private String description;

    @OneToMany(mappedBy = "vendorProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    public VendorProduct() {
    }

    public VendorProduct(ProductCatalog catalogProductId, String id, VendorProfile vendorProfile, String sku,
            BigDecimal price, Integer stockQuantity, ApprovalStatus approvalStatus,
            Boolean isActive, BigDecimal averageRating) {
        this.productCatalog = catalogProductId;
        this.id = id;
        this.vendorProfile = vendorProfile;
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

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public VendorProfile getVendorProfile() {
        return vendorProfile;
    }

    public void setVendorProfile(VendorProfile vendorProfile) {
        this.vendorProfile = vendorProfile;
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

    public Set<ProductImage> getImages() {
        return images;
    }

    public void setImages(Set<ProductImage> images) {
        this.images = images;
    }

    public void addImage(ProductImage image) {
        images.add(image);
        image.setVendorProduct(this);
    }

    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setVendorProduct(null);
    }

    @Override
    public String toString() {
        return "VendorProduct{" +
                "id='" + id + '\'' +
                ", productCatalogId=" + productCatalog.getId() +
                ", vendorId=" + vendorProfile +
                ", SKU='" + SKU + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", approvalStatus=" + approvalStatus +
                ", isActive=" + isActive +
                ", averageRating=" + averageRating +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
