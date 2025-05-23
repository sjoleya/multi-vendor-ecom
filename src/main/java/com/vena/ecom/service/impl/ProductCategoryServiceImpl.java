package com.vena.ecom.service.impl;

import com.vena.ecom.dto.response.ProductCategoryResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.repo.ProductCategoryRepository;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(this::toProductCategoryResponse).toList();
    }

    @Override
    public ProductCategoryResponse createCategory(ProductCategory category) {

        Optional<ProductCategory> existing = categoryRepository.findByName(category.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists.");
        }
        ProductCategory saved = categoryRepository.save(category);
        return toProductCategoryResponse(saved);
    }

    @Override
    public ProductCategoryResponse updateCategory(String categoryId, ProductCategory categoryDetails) {
        ProductCategory existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID:" + categoryId));
        existing.setDescription(categoryDetails.getDescription());
        existing.setName(categoryDetails.getName());
        ProductCategory saved = categoryRepository.save(existing);
        return toProductCategoryResponse(saved);
    }

    private ProductCategoryResponse toProductCategoryResponse(ProductCategory category) {
        ProductCategoryResponse dto = new ProductCategoryResponse();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.description = category.getDescription();
        return dto;
    }

    @Override
    public void deleteCategory(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with ID:" + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
