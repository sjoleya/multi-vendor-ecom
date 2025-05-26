package com.vena.ecom.dto.response;

import com.vena.ecom.model.ProductCategory;

public class ProductCategoryResponse {
    private String categoryId;
    private String name;
    private String description;

    public ProductCategoryResponse() {
    }

    public ProductCategoryResponse(ProductCategory productCategory) {
        this.categoryId = productCategory.getId();
        this.name = productCategory.getName();
        this.description = productCategory.getDescription();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
}
