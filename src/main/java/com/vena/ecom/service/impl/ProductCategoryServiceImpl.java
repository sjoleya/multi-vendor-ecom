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
                .map(ProductCategoryResponse::new)
                .toList();
        logger.info("ProductCategoryService::getAllCategories - Retrieved {} categories", categories.size());
        return categories;
    }

    @Override
    public ProductCategoryResponse createCategory(com.vena.ecom.dto.request.CreateProductCategory categoryDto) {
        logger.info("ProductCategoryService::createCategory - Creating category with name: {}", categoryDto.getName());

        Optional<ProductCategory> existing = categoryRepository.findByName(categoryDto.getName());
        if (existing.isPresent()) {
            logger.warn("ProductCategoryService::createCategory - Category creation failed: name '{}' already exists",
                    categoryDto.getName());
            throw new IllegalArgumentException("Category with this name already exists.");
        }

        ProductCategory category = new ProductCategory();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        ProductCategory saved = categoryRepository.save(category);
        logger.info("ProductCategoryService::createCategory - Category created successfully with ID: {}",
                saved.getId());

        return new ProductCategoryResponse(saved);
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
        logger.info("ProductCategoryService::updateCategory - Category updated successfully with ID: {}",
                updated.getId());

        return new ProductCategoryResponse(updated);
    }

    @Override
    public void deleteCategory(String categoryId) {
        logger.info("ProductCategoryService::deleteCategory - Deleting category with ID: {}", categoryId);

        if (!categoryRepository.existsById(categoryId)) {
            logger.error("ProductCategoryService::deleteCategory - Deletion failed: category not found with ID: {}",
                    categoryId);
            throw new ResourceNotFoundException("Category not found with ID: " + categoryId);
        }

        categoryRepository.deleteById(categoryId);
        logger.info("ProductCategoryService::deleteCategory - Category deleted successfully with ID: {}", categoryId);
    }
}
