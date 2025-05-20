package com.vena.ecom.service.impl;

import com.vena.ecom.Repo.ProductCategoryRepository;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    @Autowired
    public ProductCategoryServiceImpl(ProductCategoryRepository categoryReository) {
        this.categoryRepository = categoryReository;
    }

    @Override
    public List<ProductCategory> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Override
    public ProductCategory createCategory(ProductCategory category) {

        Optional<ProductCategory> existing = categoryRepository.findByName(category.getName());
          if(existing.isPresent()){
              throw new IllegalArgumentException("Category with this name already exists.");
          }

        return categoryRepository.save(category);

    }

    @Override
    public ProductCategory updateCategory(Long categoryId, ProductCategory categoryDetails) {
        ProductCategory existing = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("Category not found with ID:" +categoryId));
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Category not found with ID:" + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
