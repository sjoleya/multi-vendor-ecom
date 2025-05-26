package com.vena.ecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.service.impl.ProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<List<VendorProductResponse>> getAllProducts() {
        logger.info("GET /products - Fetching all products");
        return ResponseEntity.ok(productServiceImpl.getAllProducts());
    }

    @GetMapping("/category")
    public ResponseEntity<List<VendorProductResponse>> getAllProductsByCategory(@RequestParam String category) {
        logger.info("GET /products/category?category={} - Fetching products by category", category);
        return ResponseEntity.ok(productServiceImpl.getAllProductsByCategory(category));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<VendorProduct> getProductDetails(@PathVariable String productId) {
        logger.info("GET /products/{} - Fetching product details", productId);
        return ResponseEntity.ok(productServiceImpl.getProductDetails(productId));
    }
}
