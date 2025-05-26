package com.vena.ecom.dto.response;

import com.vena.ecom.model.ProductCatalog;

public class ProductCatalogResponse {
    private String productId;
    private String name;
    private String description;
    private String categoryId;
    private double price;
    private String status;

    public ProductCatalogResponse() {
    }

    public ProductCatalogResponse(ProductCatalog productCatalog) {
        this.productId = productCatalog.getId();
        this.name = productCatalog.getName();
        this.description = productCatalog.getDescription();
        this.categoryId = productCatalog.getCategory() != null ? productCatalog.getCategory().getId() : null;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
