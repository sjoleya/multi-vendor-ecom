package com.vena.ecom.service.impl;

import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.repo.VendorProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vena.ecom.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Override
    public List<VendorProductResponse> getAllProducts() {
        List<VendorProduct> vendorProducts = vendorProductRepository.findAll().stream()
                .filter(product -> product.getActive() && product.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .toList();
        return vendorProducts.stream()
                .map(VendorProductResponse::new)
                .toList();
    }

    @Override
    public List<VendorProductResponse> getAllProductsByCategory(String categoryName) {
        List<VendorProduct> vendorProducts = vendorProductRepository.findAllByProductCatalog_Category_Name(categoryName)
                .stream()
                .filter(product -> product.getActive() && product.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .toList();
        return vendorProducts.stream().map(VendorProductResponse::new).toList();
    }

    @Override
    public VendorProduct getProductDetails(String productId) {
        VendorProduct vendorProduct = vendorProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + productId + "not found!"));
        if (!vendorProduct.getActive() || !vendorProduct.getApprovalStatus().equals(ApprovalStatus.APPROVED)) {
            throw new IllegalArgumentException("Product not available for sale");
        }
        return vendorProduct;
    }
}
