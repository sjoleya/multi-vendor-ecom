package com.vena.ecom.service;

import com.vena.ecom.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategory> getAllCategories();

    ProductCategory createCategory(ProductCategory category);

    ProductCategory updateCategory(String categoryId, ProductCategory categoryDetails);

    void deleteCategory(String categoryId);
}
