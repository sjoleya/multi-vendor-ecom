package com.vena.ecom.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class ProductImageRequest {
    @NotBlank(message = "Image URL is required")
    @URL(message = "Image URL must be a valid URL")
    private String imageUrl;

    public ProductImageRequest() {
    }

    public ProductImageRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}