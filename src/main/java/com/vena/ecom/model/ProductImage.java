package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_images")
public class ProductImage extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "image_url", unique = true, nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_product_id", nullable = false)
    private VendorProduct vendorProduct;

    public ProductImage() {
    }

    public ProductImage(String id, String imageUrl, VendorProduct vendorProduct) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.vendorProduct = vendorProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public VendorProduct getVendorProduct() {
        return vendorProduct;
    }

    public void setVendorProduct(VendorProduct vendorProduct) {
        this.vendorProduct = vendorProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductImage that = (ProductImage) o;
        return Objects.equals(id, that.id) || Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageUrl);
    }

    @Override
    public String toString() {
        return "ProductImage{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}