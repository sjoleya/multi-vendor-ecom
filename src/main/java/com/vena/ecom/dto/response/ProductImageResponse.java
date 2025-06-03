package com.vena.ecom.dto.response;

import com.vena.ecom.model.ProductImage;
import java.util.Objects;

public class ProductImageResponse {
    private String imageId;
    private String imageUrl;
    private String vendorProductId;

    public ProductImageResponse() {
    }

    public ProductImageResponse(String imageId, String imageUrl, String vendorProductId) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
        this.vendorProductId = vendorProductId;
    }

    public ProductImageResponse(ProductImage image) {
        this.imageId = image.getId();
        this.imageUrl = image.getImageUrl();
        this.vendorProductId = image.getVendorProduct().getId();
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductImageResponse that = (ProductImageResponse) o;
        return Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageId);
    }

    @Override
    public String toString() {
        return "ProductImageResponse{" +
                "id=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                ", vendorProductId='" + vendorProductId + '\'' +
                '}';
    }
}