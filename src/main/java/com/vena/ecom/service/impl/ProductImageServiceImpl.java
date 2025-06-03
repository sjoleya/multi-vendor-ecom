package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.ProductImageRequest;
import com.vena.ecom.dto.response.ProductImageResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.ProductImage;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.repo.ProductImageRepository;
import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.service.ProductImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Override
    public ProductImageResponse addProductImage(String vendorProductId, ProductImageRequest request) {
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VendorProduct not found with ID: " + vendorProductId));

        boolean imageExists = productImageRepository.existsByVendorProductIdAndImageUrl(vendorProduct.getId(),
                request.getImageUrl());
        if (imageExists) {
            throw new IllegalArgumentException("Image with the same URL already exists for this product.");
        }

        ProductImage productImage = new ProductImage();
        productImage.setImageUrl(request.getImageUrl());
        productImage.setVendorProduct(vendorProduct);

        ProductImage savedImage = productImageRepository.save(productImage);
        return new ProductImageResponse(savedImage);
    }

    @Override
    public ProductImageResponse updateProductImage(String vendorProductId, String imageId,
            ProductImageRequest request) {
        ProductImage existingImage = productImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductImage not found with ID: " + imageId));

        existingImage.setImageUrl(request.getImageUrl());

        ProductImage updatedImage = productImageRepository.save(existingImage);
        return new ProductImageResponse(updatedImage);
    }

    @Override
    public void deleteProductImage(String id) {
        ProductImage existingImage = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "ProductImage not found with ID: " + id));
        productImageRepository.delete(existingImage);
    }

    @Override
    public List<ProductImageResponse> getProductImagesByVendorProductId(String vendorProductId) {
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "VendorProduct not found with ID: " + vendorProductId));

        return vendorProduct.getImages().stream()
                .map(ProductImageResponse::new)
                .collect(Collectors.toList());
    }
}