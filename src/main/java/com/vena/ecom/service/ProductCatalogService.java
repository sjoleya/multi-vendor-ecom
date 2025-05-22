package com.vena.ecom.service;

import com.vena.ecom.dto.AddProductCatalogRequest;
import com.vena.ecom.model.ProductCatalog;

import java.util.List;

public interface ProductCatalogService {
    List<ProductCatalog> getAllProductsCatalogs();

    ProductCatalog getproductCatalogById(String id);

    ProductCatalog createCatalogProduct(AddProductCatalogRequest addProductCatalogRequest);

    ProductCatalog updateProductCatalogById(String id, AddProductCatalogRequest addProductCatalogRequest);

    void deleteProductCatalog(String id);
}
