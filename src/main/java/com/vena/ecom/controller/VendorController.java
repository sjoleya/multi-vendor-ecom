package com.vena.ecom.controller;

import com.vena.ecom.dto.request.UpdateVendorProductRequest;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import com.vena.ecom.dto.request.AddVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProfileRequest;
import com.vena.ecom.model.enums.ItemStatus;
import com.vena.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/profile")
    public ResponseEntity<VendorProfileResponse> getVendorProfileById(@RequestParam String vendorProfileId) {
        VendorProfileResponse profile = vendorService.getVendorProfile(vendorProfileId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<VendorProfileResponse> getVendorProfileByUserId(@PathVariable String userId) {
        VendorProfileResponse profile = vendorService.getVendorProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfileResponse> updateVendorProfile(
            @RequestParam("vendorId") String vendorProfileId,
            @RequestBody UpdateVendorProfileRequest updatedProfile) {
        VendorProfileResponse profile = vendorService.updateVendorProfile(vendorProfileId, updatedProfile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/product")
    public ResponseEntity<VendorProductResponse> addVendorProduct(
            @RequestParam("vendorProfileId") String vendorProfileId,
            @RequestBody AddVendorProductRequest product) {
        VendorProductResponse savedProduct = vendorService.addVendorProduct(vendorProfileId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<VendorProductResponse>> getVendorProducts(@RequestParam String vendorProfileId) {
        List<VendorProductResponse> products = vendorService.getVendorProducts(vendorProfileId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<VendorProductResponse> getVendorProductById(@PathVariable String productId) {
        VendorProductResponse product = vendorService.getVendorProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<VendorProductResponse> updateVendorProduct(
            @PathVariable String productId,
            @RequestBody UpdateVendorProductRequest vendorProductRequest) {
        VendorProductResponse product = vendorService.updateVendorProduct(productId, vendorProductRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteVendorProduct(@PathVariable String productId) {
        vendorService.deleteVendorProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderItemResponse>> getVendorOrderItems(@RequestParam String vendorProfileId) {
        List<OrderItemResponse> orderItems = vendorService.getVendorOrderItems(vendorProfileId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/orders/items/{orderItemId}")
    public ResponseEntity<OrderItemResponse> getVendorOrderItemDetails(@PathVariable String orderItemId) {
        OrderItemResponse orderItem = vendorService.getVendorOrderItemDetails(orderItemId);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/orders/items/{orderItemId}/status")
    public ResponseEntity<OrderItemResponse> updateOrderItemStatus(
            @PathVariable String orderItemId,
            @RequestParam("status") ItemStatus status) {
        OrderItemResponse updatedItem = vendorService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(updatedItem);
    }

}
