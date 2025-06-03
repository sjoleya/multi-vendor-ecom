package com.vena.ecom.controller;

import com.vena.ecom.dto.request.CreateProductCategory;
import com.vena.ecom.dto.response.ProductCategoryResponse;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.service.ProductCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;

@RestController
public class ProductCategoryController {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryController.class);

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategoryResponse>> getAllCategories() {
        logger.info("GET /categories - Fetching all product categories");
        List<ProductCategoryResponse> categories = productCategoryService.getAllCategories();
        logger.info("Successfully fetched {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<ProductCategoryResponse> createCategory(
            @Valid @RequestBody CreateProductCategory categoryDto) {
        logger.info("POST /admin/categories - Creating category with name: {}", categoryDto.getName());
        ProductCategoryResponse createdCategory = productCategoryService.createCategory(categoryDto);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ProductCategoryResponse> updateCategory(@PathVariable String categoryId,
            @Valid @RequestBody ProductCategory categoryDetails) {
        logger.info("PUT /admin/categories/{} - Updating category", categoryId);
        ProductCategoryResponse updatedCategory = productCategoryService.updateCategory(categoryId, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        logger.info("DELETE /admin/categories/{} - Deleting category", categoryId);
        productCategoryService.deleteCategory(categoryId);
        logger.info("Category deleted successfully: {}", categoryId);
        return ResponseEntity.ok().build();
    }
}
