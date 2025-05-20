package com.vena.ecom.controller;

import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        return ResponseEntity.ok(productCategoryService.getAllCategories());
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<ProductCategory> createCategory(@RequestBody ProductCategory category) {
        return ResponseEntity.ok(productCategoryService.createCategory(category));
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<ProductCategory> updateCategory(@PathVariable String categoryId,
            @RequestBody ProductCategory categoryDetails) {
        return ResponseEntity.ok(productCategoryService.updateCategory(categoryId, categoryDetails));
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryId) {
        productCategoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }

}
