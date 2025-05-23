package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.model.ProductCategory;
import com.vena.ecom.repo.ProductCatalogRepository;
import com.vena.ecom.repo.ProductCategoryRepository;
import com.vena.ecom.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {
    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCatalogResponse> getAllProductsCatalogs() {
        return productCatalogRepository.findAll().stream().map(this::toProductCatalogResponse).toList();
    }

    @Override
    public ProductCatalogResponse getproductCatalogById(String id) {
        Optional<ProductCatalog> productCatalog = productCatalogRepository.findById(id);
        if (!productCatalog.isPresent()) {
            throw new ResourceNotFoundException("product does not exists in catalog with these id : " + id);
        }
        return toProductCatalogResponse(productCatalog.get());
    }

    @Override
    public ProductCatalogResponse createCatalogProduct(AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalog productCatalog = new ProductCatalog();
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());
        ProductCatalog saved = productCatalogRepository.save(productCatalog);
        return toProductCatalogResponse(saved);
    }

    @Override
    public ProductCatalogResponse updateProductCatalogById(String id,
            AddProductCatalogRequest addProductCatalogRequest) {

        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if (!optionalProductCatalog.isPresent()) {
            throw new ResourceNotFoundException("Product with id: " + id + "not found");
        }
        ProductCatalog productCatalog = optionalProductCatalog.get();
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());
        ProductCatalog saved = productCatalogRepository.save(productCatalog);
        return toProductCatalogResponse(saved);
    }

    private ProductCatalogResponse toProductCatalogResponse(ProductCatalog productCatalog) {
        ProductCatalogResponse dto = new ProductCatalogResponse();
        dto.productId = productCatalog.getId();
        dto.name = productCatalog.getName();
        dto.description = productCatalog.getDescription();
        dto.categoryId = productCatalog.getCategory() != null ? productCatalog.getCategory().getId() : null;
        dto.price = 0.0; // No price in ProductCatalog, set as needed
        dto.status = null; // No status in ProductCatalog, set as needed
        return dto;
    }

    @Override
    public void deleteProductCatalog(String id) {
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if (optionalProductCatalog.isPresent()) {
            productCatalogRepository.delete(optionalProductCatalog.get());
        } else {
            throw new ResourceNotFoundException("product not found with this id : " + id);
        }
    }

}
