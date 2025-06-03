package com.vena.ecom.service.impl;

import com.vena.ecom.dto.response.ProductCategoryResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.repo.ProductCategoryRepository;
import com.vena.ecom.service.ProductCategoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);

    @Autowired
    private ProductCategoryRepository categoryRepository;

    @Override
    public List<ProductCategoryResponse> getAllCategories() {
        logger.info("Retrieving all product categories");
        return categoryRepository.findAll()
                .stream()
                .map(ProductCategoryResponse::new)
                .toList();
    }

    @Override
    public ProductCategoryResponse createCategory(ProductCategory category) {
        logger.info("Creating category: {}", category.getName());
        validateCategory(category);

        categoryRepository.findByName(category.getName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Category with this name already exists.");
                });

        ProductCategory saved = categoryRepository.save(category);
        logger.info("Category created with ID: {}", saved.getId());
        return new ProductCategoryResponse(saved);
    }

    @Override
    public ProductCategoryResponse updateCategory(String categoryId, ProductCategory categoryDetails) {
        logger.info("Updating category with ID: {}", categoryId);
        validateCategory(categoryDetails);

        ProductCategory existing = findById(categoryId);
        existing.setName(categoryDetails.getName());
        existing.setDescription(categoryDetails.getDescription());

        ProductCategory updated = categoryRepository.save(existing);
        logger.info("Category updated with ID: {}", updated.getId());
        return new ProductCategoryResponse(updated);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("Deleting category with ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
        logger.info("Category deleted with ID: {}", categoryId);
    }

    public ProductCategory findById(String categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
    }

    //  Validation Logic
    private void validateCategory(ProductCategory category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be blank.");
        }

        int nameLength = category.getName().trim().length();
        if (nameLength < 2 || nameLength > 50) {
            throw new IllegalArgumentException("Category name must be between 2 and 50 characters.");
        }

        if (category.getDescription() != null && category.getDescription().length() > 255) {
            throw new IllegalArgumentException("Description can be up to 255 characters only.");
        }
    }
     //Sanjana jain
}
