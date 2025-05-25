package com.vena.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.Order;
import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.OrderStatus;
import com.vena.ecom.model.enums.UserRole;
import com.vena.ecom.repo.OrderRepository;
import com.vena.ecom.repo.ReviewRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.repo.VendorProfileRepository;
import com.vena.ecom.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    public UserResponse getUserDetails(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + "not found!"));
        return new UserResponse(user);
    }

    @Override
    public User updateUserRole(String userId, UserRole role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + "not found!"));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<VendorProfile> getAllVendorApplications() {
        List<VendorProfile> vendorProfiles = vendorProfileRepository.findAll();
        return vendorProfiles.stream().filter(profile -> profile.getApprovalStatus().equals(ApprovalStatus.PENDING))
                .toList();
    }

    @Override
    public VendorProfile approveVendorApplication(String applicationId) {
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile with id: " + applicationId + "not found!"));
        vendorProfile.setApprovalStatus(ApprovalStatus.APPROVED);
        return vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public VendorProfile rejectVendorApplication(String applicationId) {
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile with id: " + applicationId + "not found!"));
        vendorProfile.setApprovalStatus(ApprovalStatus.REJECTED);
        return vendorProfileRepository.save(vendorProfile);
    }

    @Override
    public List<VendorProduct> getPendingVendorProductApprovals() {
        List<VendorProduct> vendorProducts = vendorProductRepository.findAll();
        List<VendorProduct> vendorProductsFiltered = vendorProducts.stream()
                .filter(product -> product.getApprovalStatus().equals(ApprovalStatus.PENDING)).toList();
        return vendorProductsFiltered;
    }

    @Override
    public VendorProduct approveVendorProduct(String vendorProductId) {
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).orElseThrow(
                () -> new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!"));
        vendorProduct.setApprovalStatus(ApprovalStatus.APPROVED);
        return vendorProductRepository.save(vendorProduct);
    }

    @Override
    public VendorProduct rejectVendorProduct(String vendorProductId) {
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).orElseThrow(
                () -> new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!"));
        vendorProduct.setApprovalStatus(ApprovalStatus.REJECTED);
        return vendorProductRepository.save(vendorProduct);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderDetails(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with Id: " + orderId + "not found."));
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        Order order = getOrderDetails(orderId);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public void deleteReview(String reviewId) {
        reviewRepository.deleteById(reviewId);
    }

}
