package com.vena.ecom.controller;

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
    private  VendorService vendorService;

    @GetMapping("/profile")
    public ResponseEntity<VendorProfile> getVendorProfile(@RequestBody String vendorId){
        VendorProfile profile = vendorService.getVendorProfile(vendorId);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfile> updateVendorProfile(
            @RequestParam("vendorId") String vendorId,
            @RequestBody VendorProfile updatedProfile) {
        VendorProfile profile = vendorService.updateVendorProfile(vendorId, updatedProfile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/product")
    public ResponseEntity<VendorProduct> addVendorProduct(
            @RequestParam("vendorId") String vendorId,
            @RequestBody VendorProduct product) {
        VendorProduct savedProduct = vendorService.addVendorProduct(vendorId, product);
        return ResponseEntity.ok(savedProduct);
    }

    @GetMapping("/products")
    public ResponseEntity<List<VendorProduct>> getVendorProducts(@RequestParam String vendorId) {
        List<VendorProduct> products = vendorService.getVendorProducts(vendorId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<VendorProduct> getVendorProductById(@PathVariable Long productId) {
        VendorProduct product = vendorService.getVendorProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<VendorProduct> updateVendorProduct(
            @PathVariable Long productId,
            @RequestBody VendorProduct updatedProduct) {
        VendorProduct product = vendorService.updateVendorProduct(productId, updatedProduct);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteVendorProduct(@PathVariable Long productId) {
        vendorService.deleteVendorProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderItem>> getVendorOrderItems(@RequestParam("vendorId") String vendorId) {
        List<OrderItem> orderItems = vendorService.getVendorOrderItems(vendorId);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/orders/items/{orderItemId}")
    public ResponseEntity<OrderItem> getVendorOrderItemDetails(@PathVariable Long orderItemId) {
        OrderItem orderItem = vendorService.getVendorOrderItemDetails(orderItemId);
        return ResponseEntity.ok(orderItem);
    }

    @PutMapping("/orders/items/{orderItemId}/status")
    public ResponseEntity<OrderItem> updateOrderItemStatus(
            @PathVariable Long orderItemId,
            @RequestParam("status") ItemStatus status) {
        OrderItem updatedItem = vendorService.updateOrderItemStatus(orderItemId, status);
        return ResponseEntity.ok(updatedItem);
    }

}

