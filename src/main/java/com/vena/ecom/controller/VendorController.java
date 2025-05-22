package com.vena.ecom.controller;

import com.vena.ecom.dto.UpdateVendorProductRequest;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
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
    public ResponseEntity<VendorProfile> getVendorProfileById(@RequestParam String vendorProfileId) {
        VendorProfile profile = vendorService.getVendorProfile(vendorProfileId);
        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<VendorProfile> getVendorProfileByUserId(@PathVariable String userId) {
        VendorProfile profile = vendorService.getVendorProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfile> updateVendorProfile(
            @RequestParam("vendorId") String vendorProfileId,
            @RequestBody com.vena.ecom.dto.UpdateVendorProfileRequest updatedProfile) {
        VendorProfile profile = vendorService.updateVendorProfile(vendorProfileId, updatedProfile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/product")
    public ResponseEntity<VendorProduct> addVendorProduct(
            @RequestParam("vendorProfileId") String vendorProfileId,
            @RequestBody com.vena.ecom.dto.AddVendorProductRequest product) {
        VendorProduct savedProduct = vendorService.addVendorProduct(vendorProfileId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<VendorProduct>> getVendorProducts(@RequestParam String vendorProfileId) {
        List<VendorProduct> products = vendorService.getVendorProducts(vendorProfileId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<VendorProduct> getVendorProductById(@PathVariable String productId) {
        VendorProduct product = vendorService.getVendorProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<VendorProduct> updateVendorProduct(
            @PathVariable String productId,
            @RequestBody UpdateVendorProductRequest vendorProductRequest) {
        VendorProduct product = vendorService.updateVendorProduct(productId, vendorProductRequest);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteVendorProduct(@PathVariable String productId) {
        vendorService.deleteVendorProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderItem>> getVendorOrderItems(@RequestParam String vendorProfileId) {
        List<OrderItem> orderItems = vendorService.getVendorOrderItems(vendorProfileId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/orders/items/{orderItemId}")
    public ResponseEntity<OrderItem> getVendorOrderItemDetails(@PathVariable String orderItemId) {
        OrderItem orderItem = vendorService.getVendorOrderItemDetails(orderItemId);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/orders/items/{orderItemId}/status")
    public ResponseEntity<OrderItem> updateOrderItemStatus(
            @PathVariable String orderItemId,
            @RequestParam("status") ItemStatus status) {
        OrderItem updatedItem = vendorService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(updatedItem);
    }

}
