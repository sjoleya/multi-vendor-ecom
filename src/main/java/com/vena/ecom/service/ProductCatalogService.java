package com.vena.ecom.service;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;

import java.util.List;

public interface ProductCatalogService {
    List<ProductCatalogResponse> getAllProductsCatalogs();

    ProductCatalogResponse getproductCatalogById(String id);

    ProductCatalogResponse createCatalogProduct(AddProductCatalogRequest addProductCatalogRequest);

    ProductCatalogResponse updateProductCatalogById(String id, AddProductCatalogRequest addProductCatalogRequest);

    void deleteProductCatalog(String id);
}
