package com.vena.ecom.service.impl;

import com.vena.ecom.dto.AddProductCatalogRequest;
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
    public List<ProductCatalog> getAllProductsCatalogs() {
        return productCatalogRepository.findAll();
    }

    @Override
    public ProductCatalog getproductCatalogById(String id) {
        Optional<ProductCatalog> productCatalog = productCatalogRepository.findById(id);
        if (!productCatalog.isPresent()) {
            throw new ResourceNotFoundException("product does not exists in catalog with these id : " + id);
        }
        return productCatalog.get();
    }

    @Override
    public ProductCatalog createCatalogProduct(AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalog productCatalog = new ProductCatalog();
        ProductCategory category = productCategoryRepository.findById(addProductCatalogRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
        productCatalog.setName(addProductCatalogRequest.getName());
        productCatalog.setDescription(addProductCatalogRequest.getDescription());
        productCatalog.setGlobalSKU(addProductCatalogRequest.getGlobalSKU());
        productCatalog.setCategory(category);
        productCatalog.setBrand(addProductCatalogRequest.getBrand());
        return productCatalogRepository.save(productCatalog);
    }

    @Override
    public ProductCatalog updateProductCatalogById(String id, AddProductCatalogRequest addProductCatalogRequest) {

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
        return productCatalogRepository.save(productCatalog);
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
