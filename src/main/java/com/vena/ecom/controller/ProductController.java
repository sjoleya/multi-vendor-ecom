package com.vena.ecom.controller;

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

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<List<VendorProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productServiceImpl.getAllProducts());
    }

    @GetMapping("/category")
    public ResponseEntity<List<VendorProductResponse>> getAllProductsByCategory(@RequestParam String category) {
        return ResponseEntity.ok(productServiceImpl.getAllProductsByCategory(category));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<VendorProduct> getProductDetails(@PathVariable String productId) {
        return ResponseEntity.ok(productServiceImpl.getProductDetails(productId));
    }
}
