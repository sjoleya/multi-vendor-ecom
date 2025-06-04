package com.vena.ecom.service;

import com.vena.ecom.dto.response.ProductCategoryResponse;
import com.vena.ecom.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryResponse> getAllCategories();

    ProductCategoryResponse createCategory(com.vena.ecom.dto.request.CreateProductCategory categoryDto);

    ProductCategoryResponse updateCategory(String categoryId, ProductCategory categoryDetails);

    void deleteCategory(String categoryId);
}
