package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.model.User;
import com.vena.ecom.dto.response.OrderResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.model.enums.OrderStatus;

public interface AdminService {
    List<com.vena.ecom.dto.response.UserResponse> getAllUsers();

    com.vena.ecom.dto.response.UserResponse getUserDetails(String userId);

    User updateUserRole(String userId, UserRole role);

    List<VendorProfileResponse> getAllVendorApplications();

    VendorProfileResponse approveVendorApplication(String applicationId);

    VendorProfileResponse rejectVendorApplication(String applicationId);

    List<VendorProductResponse> getPendingVendorProductApprovals();

    VendorProductResponse approveVendorProduct(String vendorProductId);

    VendorProductResponse rejectVendorProduct(String vendorProductId);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderDetails(String orderId);

    OrderResponse updateOrderStatus(String orderId, OrderStatus status);

    void deleteReview(String reviewId);

}
