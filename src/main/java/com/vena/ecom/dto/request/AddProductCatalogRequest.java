package com.vena.ecom.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AddProductCatalogRequest {
    @NotBlank(message = "Product name is required")
    private String name;
    @NotBlank(message = "Brand is required")
    private String brand;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Global SKU is required")
    private String globalSKU;
    @NotBlank(message = "Category ID is required")
    private String categoryId;

    public AddProductCatalogRequest() {
    }

    public AddProductCatalogRequest(String name, String brand, String description, String globalSKU,
            String categoryId) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.globalSKU = globalSKU;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlobalSKU() {
        return globalSKU;
    }

    public void setGlobalSKU(String globalSKU) {
        this.globalSKU = globalSKU;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
