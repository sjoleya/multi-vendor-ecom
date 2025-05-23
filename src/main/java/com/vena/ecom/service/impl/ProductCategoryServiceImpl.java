package com.vena.ecom.service.impl;

import com.vena.ecom.dto.response.ProductCategoryResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.repo.ProductCategoryRepository;
import com.vena.ecom.model.ProductCategory;
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
        logger.info("ProductCategoryService::getAllCategories - Retrieving all categories");
        List<ProductCategoryResponse> categories = categoryRepository.findAll()
                .stream()
                .map(this::toProductCategoryResponse)
                .toList();
        logger.info("ProductCategoryService::getAllCategories - Retrieved {} categories", categories.size());
        return categories;
    }

    @Override
    public ProductCategoryResponse createCategory(ProductCategory category) {
        logger.info("ProductCategoryService::createCategory - Creating category with name: {}", category.getName());

        Optional<ProductCategory> existing = categoryRepository.findByName(category.getName());
        if (existing.isPresent()) {
            logger.warn("ProductCategoryService::createCategory - Category creation failed: name '{}' already exists", category.getName());
            throw new IllegalArgumentException("Category with this name already exists.");
        }

        ProductCategory saved = categoryRepository.save(category);
        logger.info("ProductCategoryService::createCategory - Category created successfully with ID: {}", saved.getId());

        return toProductCategoryResponse(saved);
    }

    @Override
    public ProductCategoryResponse updateCategory(String categoryId, ProductCategory categoryDetails) {
        logger.info("ProductCategoryService::updateCategory - Updating category with ID: {}", categoryId);

        ProductCategory existing = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    logger.error("ProductCategoryService::updateCategory - Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category not found with ID: " + categoryId);
                });

        existing.setName(categoryDetails.getName());
        existing.setDescription(categoryDetails.getDescription());

        ProductCategory updated = categoryRepository.save(existing);
        logger.info("ProductCategoryService::updateCategory - Category updated successfully with ID: {}", updated.getId());

        return toProductCategoryResponse(updated);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("ProductCategoryService::deleteCategory - Deleting category with ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            logger.error("ProductCategoryService::deleteCategory - Deletion failed: category not found with ID: {}", categoryId);
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }

        categoryRepository.deleteById(categoryId);
        logger.info("ProductCategoryService::deleteCategory - Category deleted successfully with ID: {}", categoryId);
    }

    private ProductCategoryResponse toProductCategoryResponse(ProductCategory category) {
        logger.debug("ProductCategoryService::toProductCategoryResponse - Mapping entity to DTO for category ID: {}", category.getId());
        ProductCategoryResponse dto = new ProductCategoryResponse();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.description = category.getDescription();
        return dto;
    }
}
