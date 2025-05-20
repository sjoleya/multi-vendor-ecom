package com.vena.ecom.service.impl;

import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.repo.ProductCatalogRepository;
import com.vena.ecom.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {
    @Autowired
    private ProductCatalogRepository productCatalogRepository;

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
    public ProductCatalog createCatalogProduct(ProductCatalog productCatalog) {
        if (productCatalogRepository.existsById(productCatalog.getCatalogId())) {
            throw new IllegalArgumentException("product already exists");
        }
        return productCatalogRepository.save(productCatalog);
    }

    @Override
    public ProductCatalog updateProductCatalogById(String id, ProductCatalog productCatalog) {

        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if (!optionalProductCatalog.isPresent()) {
            throw new ResourceNotFoundException("Product with id: " + id + "not found");
        }
        ProductCatalog pc;
        pc = optionalProductCatalog.get();
        pc.setName(productCatalog.getName());
        pc.setBrand(productCatalog.getBrand());
        pc.setDescription(productCatalog.getDescription());
        pc.setGlobalSKU(productCatalog.getGlobalSKU());
        pc.setCategoryId(productCatalog.getCategoryId());
        productCatalogRepository.save(pc);
        return pc;

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
