package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.repo.ProductCatalogRepository;
import com.vena.ecom.repo.ProductCategoryRepository;
import com.vena.ecom.service.ProductCatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    private static final Logger logger = LoggerFactory.getLogger(ProductCatalogServiceImpl.class);

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCatalogResponse> getAllProductsCatalogs() {
        logger.info("Fetching all product catalogs");
        List<ProductCatalogResponse> responses = productCatalogRepository.findAll()
                .stream()
                .map(ProductCatalogResponse::new)
                .toList();
        logger.debug("Total product catalogs found: {}", responses.size());
        return responses;
    }

    @Override
    public ProductCatalogResponse getproductCatalogById(String id) {
        logger.info("Fetching product catalog by ID: {}", id);
        ProductCatalog productCatalog = productCatalogRepository.findById(id).orElseThrow(() -> {
            logger.warn("Product catalog not found for ID: {}", id);
            return new ResourceNotFoundException("Product with id: " + id + "not found in catalog!");
        });

        logger.debug("Product catalog found: {}", productCatalog.getName());
        return new ProductCatalogResponse(productCatalog);
    }

    @Override
    public ProductCatalogResponse createCatalogProduct(AddProductCatalogRequest addProductCatalogRequest) {
        logger.info("Creating new product catalog: {}", addProductCatalogRequest.getName());
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> {
                    logger.warn("Category not found with ID: {}", addProductCatalogRequest.getCategoryId());
                    return new ResourceNotFoundException("Category not found!");
                });

        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());

        ProductCatalog saved = productCatalogRepository.save(productCatalog);
        logger.debug("Product catalog saved with ID: {}", saved.getId());
        return new ProductCatalogResponse(saved);
    }

    @Override
    public ProductCatalogResponse updateProductCatalogById(String id,
            AddProductCatalogRequest addProductCatalogRequest) {
        logger.info("Updating product catalog with ID: {}", id);
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if (!optionalProductCatalog.isPresent()) {
            logger.warn("Product catalog not found for update with ID: {}", id);
            throw new ResourceNotFoundException("Product with id: " + id + " not found");
        }

        ProductCatalog productCatalog = optionalProductCatalog.get();
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> {
                    logger.warn("Category not found with ID: {}", addProductCatalogRequest.getCategoryId());
                    return new ResourceNotFoundException("Category not found!");
                });

        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());

        ProductCatalog saved = productCatalogRepository.save(productCatalog);
        logger.debug("Product catalog updated with ID: {}", saved.getId());
        return new ProductCatalogResponse(saved);
    }

    @Override
    public void deleteProductCatalog(String id) {
        logger.info("Deleting product catalog with ID: {}", id);
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if (optionalProductCatalog.isPresent()) {
            productCatalogRepository.delete(optionalProductCatalog.get());
            logger.info("Product catalog deleted successfully with ID: {}", id);
        } else {
            logger.warn("Attempted to delete non-existent product catalog with ID: {}", id);
            throw new ResourceNotFoundException("Product not found with this ID: " + id);
        }
    }
}
