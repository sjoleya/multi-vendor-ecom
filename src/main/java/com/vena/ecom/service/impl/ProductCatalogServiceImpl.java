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
        return productCatalogRepository.findAll().stream().map(ProductCatalogResponse::new).toList();
    }

    @Override
    public ProductCatalogResponse getproductCatalogById(String id) {
        ProductCatalog productCatalog = productCatalogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "product does not exists in catalog with these id : " + id));
        return new ProductCatalogResponse(productCatalog);
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
        return new ProductCatalogResponse(saved);
    }

    @Override
    public ProductCatalogResponse updateProductCatalogById(String id,
            AddProductCatalogRequest addProductCatalogRequest) {

        ProductCatalog productCatalog = productCatalogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + id + "not found"));
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());
        ProductCatalog saved = productCatalogRepository.save(productCatalog);
        return new ProductCatalogResponse(saved);
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
