package com.vena.ecom.service;

import com.vena.ecom.dto.request.ProductImageRequest;
import com.vena.ecom.dto.response.ProductImageResponse;

import java.util.List;

public interface ProductImageService {
    ProductImageResponse addProductImage(String vendorProductId, ProductImageRequest request);

    ProductImageResponse updateProductImage(String vendorProductId, String imageId, ProductImageRequest request);

    void deleteProductImage(String id);

    List<ProductImageResponse> getProductImagesByVendorProductId(String vendorProductId);
}