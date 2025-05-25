package com.vena.ecom.service;

import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.model.VendorProduct;

import java.util.List;

public interface ProductService {

    List<VendorProductResponse> getAllProducts();

    List<VendorProductResponse> getAllProductsByCategory(String categoryName);

    VendorProduct getProductDetails(String productId);
}
