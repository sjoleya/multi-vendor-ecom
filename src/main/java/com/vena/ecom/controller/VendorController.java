package com.vena.ecom.controller;

import com.vena.ecom.dto.request.UpdateVendorProductRequest;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import com.vena.ecom.dto.request.AddVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProfileRequest;
import com.vena.ecom.model.enums.ItemStatus;
import com.vena.ecom.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/vendor")
public class VendorController {
    private static final Logger logger =  LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @GetMapping("/profile")
    public ResponseEntity<VendorProfileResponse> getVendorProfileById(@RequestParam String vendorProfileId) {
       logger.info("GET/vendor/profile - Fetching vendor profile with ID: {}",vendorProfileId);
        VendorProfileResponse profile = vendorService.getVendorProfile(vendorProfileId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<VendorProfileResponse> getVendorProfileByUserId(@PathVariable String userId) {
        logger.info("GET/vendor/profile/{} - Fetching vendor profile" ,userId);
        VendorProfileResponse profile = vendorService.getVendorProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfileResponse> updateVendorProfile(
            @RequestParam("vendorProfileId") String vendorProfileId,
            @RequestBody UpdateVendorProfileRequest updatedProfile) {
        logger.info("PUT/vendor/profile - Updating vendor profile with ID: {}", vendorProfileId);
        VendorProfileResponse profile = vendorService.updateVendorProfile(vendorProfileId, updatedProfile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/product")
    public ResponseEntity<VendorProductResponse> addVendorProduct(
            @RequestParam("vendorProfileId") String vendorProfileId,
            @RequestBody AddVendorProductRequest product) {
        logger.info("POST/vendor/product - Adding product for vendor profile ID: {}", vendorProfileId);
        VendorProductResponse savedProduct = vendorService.addVendorProduct(vendorProfileId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<VendorProductResponse>> getVendorProducts(@RequestParam String vendorProfileId) {
        logger.info("GET/vendor/products - Fetching products for vendor profile ID: {}", vendorProfileId);
        List<VendorProductResponse> products = vendorService.getVendorProducts(vendorProfileId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<VendorProductResponse> getVendorProductById(@PathVariable String productId) {
        logger.info(" GET/vendor/products/{} - Fetching vendor product", productId);
        VendorProductResponse product = vendorService.getVendorProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<VendorProductResponse> updateVendorProduct(
            @PathVariable String productId,
            @RequestBody UpdateVendorProductRequest vendorProductRequest) {
        logger.info("PUT/vendor/products/{} - Updating vendor product", productId);
        VendorProductResponse product = vendorService.updateVendorProduct(productId, vendorProductRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteVendorProduct(@PathVariable String productId) {
        logger.info("DELETE/vendor/products/{} - Deleting vendor product", productId);
        vendorService.deleteVendorProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderItemResponse>> getVendorOrderItems(@RequestParam String vendorProfileId) {
        logger.info("GET/vendor/orders - Fetching order items for vendor profile ID: {}", vendorProfileId);
        List<OrderItemResponse> orderItems = vendorService.getVendorOrderItems(vendorProfileId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/orders/items/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getVendorOrderItemDetails(@PathVariable String orderItemId) {
        logger.info("GET/vendor/orders/items/{} - Fetching details for order item", orderItemId);
        OrderItemResponse orderItem = vendorService.getVendorOrderItemDetails(orderItemId);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/orders/items/{orderItemId}/status")
    public ResponseEntity<OrderItemResponse> updateOrderItemStatus(
            @PathVariable String orderItemId,
            @RequestParam("status") ItemStatus status) {
        logger.info("PUT/vendor/orders/items/{}/{} - Updating status of order item ", orderItemId, status);
        OrderItemResponse updatedItem = vendorService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(updatedItem);
    }

}
