package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.model.Order;
import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.model.enums.OrderStatus;

public interface AdminService {
    List<com.vena.ecom.dto.response.UserResponse> getAllUsers();

    com.vena.ecom.dto.response.UserResponse getUserDetails(String userId);

    User updateUserRole(String userId, UserRole role);

    List<VendorProfile> getAllVendorApplications();

    VendorProfile approveVendorApplication(String applicationId);

    VendorProfile rejectVendorApplication(String applicationId);

    List<VendorProduct> getPendingVendorProductApprovals();

    VendorProduct approveVendorProduct(String vendorProductId);

    VendorProduct rejectVendorProduct(String vendorProductId);

    List<Order> getAllOrders();

    Order getOrderDetails(String orderId);

    Order updateOrderStatus(String orderId, OrderStatus status);

    void deleteReview(String reviewId);

}
