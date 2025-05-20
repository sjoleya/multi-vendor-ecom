package com.vena.ecom.service.impl;

import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.repo.productCatalogRepository;
import com.vena.ecom.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class ProductCatalogServiceImpl implements ProductCatalogService {
    @Autowired
    private productCatalogRepository productCatalogRepository;

    @Override
    public List<ProductCatalog> getAllProductsCatalogs() {
        return productCatalogRepository.findAll();
    }

    @Override
    public ProductCatalog getproductCatalogById(String id) {
        Optional<ProductCatalog> productCatalog = productCatalogRepository.findById(id);
        if(productCatalog.isPresent()) {
            return productCatalog.get();
        }
        else {
            throw new RuntimeException("product does not exists in catalog with these id : "+ id);
        }
    }

    @Override
    public ProductCatalog createCatalogProduct(ProductCatalog productCatalog) {
        if(productCatalogRepository.existsById(productCatalog.getCatalogId())) {
            throw new RuntimeException("product already exists");
        }
        return productCatalogRepository.save(productCatalog);
    }

    @Override
    public ProductCatalog updateProductCatalogById(String id,ProductCatalog productCatalog) {

        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        ProductCatalog pc;
        if(optionalProductCatalog.isPresent()) {
            pc = optionalProductCatalog.get();
            pc.setName(productCatalog.getName());
            pc.setDescription(productCatalog.getDescription());
            pc.setGlobalSKU(productCatalog.getGlobalSKU());
            pc.setCatagoryId(productCatalog.getCategoryId());
            productCatalogRepository.save(pc);
            return pc;
        }
        else {
            throw new RuntimeException("product not updated");
        }
    }

    @Override
    public void deleteProductCatalog(String id) {
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepository.findById(id);
        if(optionalProductCatalog.isPresent()) {
            productCatalogRepository.delete(optionalProductCatalog.get());
        }
        else {
            throw new RuntimeException("product not found with this id : " + id);
        }
    }

}
