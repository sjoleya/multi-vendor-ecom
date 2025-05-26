package com.vena.ecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.vena.ecom.dto.request.AddProductCatalogRequest;
import com.vena.ecom.dto.response.ProductCatalogResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.service.impl.ProductCatalogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductCatalogServiceImpl productCatalogService;

    // Users Endpoints
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserDetails(@PathVariable String userId) {
        return ResponseEntity.ok(adminService.getUserDetails(userId));
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable String userId, @RequestParam UserRole role) {
        logger.info("PUT /admin/users/{}/role - Updating user role", userId);
        return ResponseEntity.ok(adminService.updateUserRole(userId, role));
    }

    // Vendor Applications Endpoints
    @GetMapping("/vendor-applications")
    public ResponseEntity<List<VendorProfile>> getAllVendorApplications() {
        logger.info("GET /admin/vendor-applications - Fetching all vendor applications");
        return ResponseEntity.ok(adminService.getAllVendorApplications());
    }

    @PutMapping("/vendor-applications/{applicationId}/approve")
    public ResponseEntity<VendorProfile> approveVendorApplication(@PathVariable String applicationId) {
        logger.info("PUT /admin/vendor-applications/{}/approve - Approving vendor application", applicationId);
        return ResponseEntity.ok(adminService.approveVendorApplication(applicationId));
    }

    @PutMapping("/vendor-applications/{applicationId}/reject")
    public ResponseEntity<VendorProfile> rejectVendorApplication(@PathVariable String applicationId) {
        logger.info("PUT /admin/vendor-applications/{}/reject - Rejecting vendor application", applicationId);
        return ResponseEntity.ok(adminService.rejectVendorApplication(applicationId));
    }

    // Vendor Product Approval Endpoints
    @GetMapping("/vendor-products/pending-approval")
    public ResponseEntity<List<VendorProduct>> getPendingVendorProductApprovals() {
        logger.info("GET /admin/vendor-products/pending-approval - Fetching pending vendor product approvals");
        return ResponseEntity.ok(adminService.getPendingVendorProductApprovals());
    }

    @PutMapping("/vendor-products/{vendorProductId}/approve")
    public ResponseEntity<VendorProduct> approveVendorProduct(@PathVariable String vendorProductId) {
        logger.info("PUT /admin/vendor-products/{}/approve - Approving vendor product", vendorProductId);
        return ResponseEntity.ok(adminService.approveVendorProduct(vendorProductId));
    }

    @PutMapping("/vendor-products/{vendorProductId}/reject")
    public ResponseEntity<VendorProduct> rejectVendorProduct(@PathVariable String vendorProductId) {
        logger.info("PUT /admin/vendor-products/{}/reject - Rejecting vendor product", vendorProductId);
        return ResponseEntity.ok(adminService.rejectVendorProduct(vendorProductId));
    }

    // Orders & Reviews Endpoints
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("GET /admin/orders - Fetching all orders");
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable String orderId) {
        logger.info("GET /admin/orders/{} - Fetching order details", orderId);
        return ResponseEntity.ok(adminService.getOrderDetails(orderId));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId,
            @RequestParam OrderStatus status) {
        logger.info("PUT /admin/orders/{}/status - Updating order status", orderId);
        return ResponseEntity.ok(adminService.updateOrderStatus(orderId, status));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        logger.info("DELETE /admin/reviews/{} - Deleting review", reviewId);
        adminService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    // Product Catalog Endpoints
    @GetMapping("/catalog")
    public ResponseEntity<List<ProductCatalogResponse>> getAllProductCatalogs() {
        List<ProductCatalogResponse> productCatalogList = productCatalogService.getAllProductsCatalogs();
        return new ResponseEntity<>(productCatalogList, HttpStatus.OK);
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<ProductCatalogResponse> getProductCatalogById(@PathVariable String id) {
        ProductCatalogResponse productCatalog = productCatalogService.getproductCatalogById(id);
        return ResponseEntity.ok(productCatalog);
    }

    @PostMapping("/catalog")
    public ResponseEntity<ProductCatalogResponse> createProductCatalog(
            @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalogResponse createdCatalog = productCatalogService.createCatalogProduct(addProductCatalogRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCatalog);
    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<ProductCatalogResponse> updateProductCatalog(@PathVariable String id,
            @RequestBody AddProductCatalogRequest addProductCatalogRequest) {
        ProductCatalogResponse updatedCatalog = productCatalogService.updateProductCatalogById(id,
                addProductCatalogRequest);
        return ResponseEntity.ok(updatedCatalog);
    }

    @DeleteMapping("/catalog/{id}")
    public ResponseEntity<String> deleteProductCatalog(@PathVariable String id) {
        productCatalogService.deleteProductCatalog(id);
        return ResponseEntity.ok("Product catalog deleted successfully.");
    }
}
