package com.vena.ecom.service.impl;

import com.vena.ecom.model.ProductCatalog;
import com.vena.ecom.repo.ProductCatalogRepositiory;
import com.vena.ecom.service.ProductCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public  class ProductCatalogServiceImpl implements ProductCatalogService {
    @Autowired
    ProductCatalogRepositiory productCatalogRepositiory;

    @Override
    public List<ProductCatalog> getAllProductsCatalogs() {
        List<ProductCatalog> pclist;
        pclist = productCatalogRepositiory.findAll();
        return pclist;
    }

    @Override
    public ProductCatalog getproductCatalogById(String id) {
        Optional<ProductCatalog> productCatalog = productCatalogRepositiory.findById(id);
        if(productCatalog.isPresent()) {
            return productCatalog.get();
        }
        else {
            throw new RuntimeException("product catalog does not exists with these id : "+ id);
        }
    }

    @Override
    public ProductCatalog createCatalogProduct(ProductCatalog productCatalog) {
        if(productCatalogRepositiory.existsById(productCatalog.getCatalogId())) {
            throw new RuntimeException("product catalog already exists");
        }
        return productCatalogRepositiory.save(productCatalog);
    }

    @Override
    public ProductCatalog updateProductCatalogById(String id,ProductCatalog productCatalog) {

        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepositiory.findById(id);
        ProductCatalog pc;
        if(optionalProductCatalog.isPresent()) {
            pc = optionalProductCatalog.get();
            pc.setName(productCatalog.getName());
            pc.setDescription(productCatalog.getDescription());
            pc.setGlobalSKU(productCatalog.getGlobalSKU());
            pc.setCatagoryId(productCatalog.getCategoryId());
            return pc;
        }
        else {
            throw new RuntimeException("student not updated");
        }
    }

    @Override
    public void deleteProductCatalog(String id) {
        Optional<ProductCatalog> optionalProductCatalog = productCatalogRepositiory.findById(id);
        ProductCatalog pc;
        if(optionalProductCatalog.isPresent()) {
            productCatalogRepositiory.delete(optionalProductCatalog.get());
        }
        else {
            throw new RuntimeException("student not found with these id : " + id);
        }
    }

}
