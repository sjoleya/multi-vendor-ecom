package com.vena.ecom.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Override
    public List<VendorProductResponse> getAllProducts() {
        logger.info("Fetching all products");
        List<VendorProduct> vendorProducts = vendorProductRepository.findAll().stream()
                .filter(product -> product.getActive() && product.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .toList();
        return vendorProducts.stream()
                .map(VendorProductResponse::new)
                .toList();
    }

    @Override
    public List<VendorProductResponse> getAllProductsByCategory(String categoryName) {
        logger.info("Fetching all products by category: {}", categoryName);
        List<VendorProduct> vendorProducts = vendorProductRepository.findAllByProductCatalogCategoryName(categoryName)
                .stream()
                .filter(product -> product.getActive() && product.getApprovalStatus().equals(ApprovalStatus.APPROVED))
                .toList();
        return vendorProducts.stream().map(VendorProductResponse::new).toList();
    }

    @Override
    public VendorProduct getProductDetails(String productId) {
        logger.info("Fetching product details for product id: {}", productId);
        VendorProduct vendorProduct = vendorProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id: " + productId + "not found!"));
        if (!vendorProduct.getActive() || !vendorProduct.getApprovalStatus().equals(ApprovalStatus.APPROVED)) {
            throw new IllegalArgumentException("Product not available for sale");
        }
        return vendorProduct;
    }
}
