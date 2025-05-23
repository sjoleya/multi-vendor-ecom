package com.vena.ecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    // Users Endpoints
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Fetching all users");
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserDetails(@PathVariable String userId) {
        logger.info("Fetching user details for user ID: {}", userId);
        return ResponseEntity.ok(adminService.getUserDetails(userId));
    }

    @PutMapping("/users/{userId}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable String userId, @RequestParam UserRole role) {
        logger.info("Updating role for user ID: {} to role: {}", userId, role);
        return ResponseEntity.ok(adminService.updateUserRole(userId, role));
    }

    // Vendor Applications Endpoints
    @GetMapping("/vendor-applications")
    public ResponseEntity<List<VendorProfile>> getAllVendorApplications() {
        logger.info("Fetching all vendor applications");
        return ResponseEntity.ok(adminService.getAllVendorApplications());
    }

    @PutMapping("/vendor-applications/{applicationId}/approve")
    public ResponseEntity<VendorProfile> approveVendorApplication(@PathVariable String applicationId) {
        logger.info("Approving vendor application with ID: {}", applicationId);
        return ResponseEntity.ok(adminService.approveVendorApplication(applicationId));
    }

    @PutMapping("/vendor-applications/{applicationId}/reject")
    public ResponseEntity<VendorProfile> rejectVendorApplication(@PathVariable String applicationId) {
        logger.info("Rejecting vendor application with ID: {}", applicationId);
        return ResponseEntity.ok(adminService.rejectVendorApplication(applicationId));
    }

    // Vendor Product Approval Endpoints
    @GetMapping("/vendor-products/pending-approval")
    public ResponseEntity<List<VendorProduct>> getPendingVendorProductApprovals() {
        logger.info("Fetching pending vendor product approvals");
        return ResponseEntity.ok(adminService.getPendingVendorProductApprovals());
    }

    @PutMapping("/vendor-products/{vendorProductId}/approve")
    public ResponseEntity<VendorProduct> approveVendorProduct(@PathVariable String vendorProductId) {
        logger.info("Approving vendor product with ID: {}", vendorProductId);
        return ResponseEntity.ok(adminService.approveVendorProduct(vendorProductId));
    }

    @PutMapping("/vendor-products/{vendorProductId}/reject")
    public ResponseEntity<VendorProduct> rejectVendorProduct(@PathVariable String vendorProductId) {
        logger.info("Rejecting vendor product with ID: {}", vendorProductId);
        return ResponseEntity.ok(adminService.rejectVendorProduct(vendorProductId));
    }

    // Orders & Reviews Endpoints
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Fetching all orders");
        return ResponseEntity.ok(adminService.getAllOrders());
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderDetails(@PathVariable String orderId) {
        logger.info("Fetching order details for order ID: {}", orderId);
        return ResponseEntity.ok(adminService.getOrderDetails(orderId));
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId,
            @RequestParam OrderStatus status) {
        logger.info("Updating order status for order ID: {} to status: {}", orderId, status);
        return ResponseEntity.ok(adminService.updateOrderStatus(orderId, status));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId) {
        logger.info("Deleting review with ID: {}", reviewId);
        adminService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }
}
