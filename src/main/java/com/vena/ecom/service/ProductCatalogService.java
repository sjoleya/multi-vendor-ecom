package com.vena.ecom.service;

import com.vena.ecom.model.ProductCatalog;

import java.util.List;

public interface ProductCatalogService {
    List<ProductCatalog> getAllProductsCatalogs();
    ProductCatalog getproductCatalogById(String id);
    ProductCatalog createCatalogProduct(ProductCatalog productCatalog);
    ProductCatalog updateProductCatalogById(String id, ProductCatalog productCatalog);
    void deleteProductCatalog(String id);
}
