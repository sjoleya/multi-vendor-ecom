package com.vena.ecom.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

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
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserDetails(String userId) {
        logger.info("Fetching user details for user ID: {}", userId);
        return userRepository.findById(userId).map(user -> {
            logger.info("User found with id: {}", userId);
            return user;
        }).orElseThrow(() -> {
            logger.warn("User not found with id: {}", userId);
            return new ResourceNotFoundException("User with id: " + userId + "not found!");
        });
    }

    @Override
    public User updateUserRole(String userId, UserRole role) {
        logger.info("Updating role for user ID: {} to role: {}", userId, role);
        User user = userRepository.findById(userId).map(u -> {
            logger.info("User found with id: {}", userId);
            return u;
        }).orElseThrow(() -> {
            logger.warn("User not found with id: {}", userId);
            return new ResourceNotFoundException("User with id: " + userId + "not found!");
        });
        user.setRole(role);
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error while saving user with id: {}", userId, e);
            throw e;
        }
    }

    @Override
    public List<VendorProfile> getAllVendorApplications() {
        logger.info("Fetching all vendor applications");
        List<VendorProfile> vendorProfiles = vendorProfileRepository.findAll();
        return vendorProfiles.stream().filter(profile -> profile.getApprovalStatus().equals(ApprovalStatus.PENDING))
                .toList();
    }

    @Override
    public VendorProfile approveVendorApplication(String applicationId) {
        logger.info("Approving vendor application with ID: {}", applicationId);
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId).map(vp -> {
            logger.info("Vendor profile found with id: {}", applicationId);
            return vp;
        }).orElseThrow(() -> {
            logger.warn("Vendor profile not found with id: {}", applicationId);
            return new ResourceNotFoundException("Profile with id: " + applicationId + "not found!");
        });
        vendorProfile.setApprovalStatus(ApprovalStatus.APPROVED);
        try {
            return vendorProfileRepository.save(vendorProfile);
        } catch (Exception e) {
            logger.error("Error while saving vendor profile with id: {}", applicationId, e);
            throw e;
        }
    }

    @Override
    public VendorProfile rejectVendorApplication(String applicationId) {
        logger.info("Rejecting vendor application with ID: {}", applicationId);
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId).map(vp -> {
            logger.info("Vendor profile found with id: {}", applicationId);
            return vp;
        }).orElseThrow(() -> {
            logger.warn("Vendor profile not found with id: {}", applicationId);
            return new ResourceNotFoundException("Profile with id: " + applicationId + "not found!");
        });
        vendorProfile.setApprovalStatus(ApprovalStatus.REJECTED);
        try {
            return vendorProfileRepository.save(vendorProfile);
        } catch (Exception e) {
            logger.error("Error while saving vendor profile with id: {}", applicationId, e);
            throw e;
        }
    }

    @Override
    public List<VendorProduct> getPendingVendorProductApprovals() {
        logger.info("Fetching pending vendor product approvals");
        List<VendorProduct> vendorProducts = vendorProductRepository.findAll();
        List<VendorProduct> vendorProductsFiltered = vendorProducts.stream()
                .filter(product -> product.getApprovalStatus().equals(ApprovalStatus.PENDING)).toList();
        return vendorProductsFiltered;
    }

    @Override
    public VendorProduct approveVendorProduct(String vendorProductId) {
        logger.info("Approving vendor product with ID: {}", vendorProductId);
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).map(vp -> {
            logger.info("Vendor product found with id: {}", vendorProductId);
            return vp;
        }).orElseThrow(
                () -> {
                    logger.warn("Vendor product not found with id: {}", vendorProductId);
                    return new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!");
                });
        vendorProduct.setApprovalStatus(ApprovalStatus.APPROVED);
        try {
            return vendorProductRepository.save(vendorProduct);
        } catch (Exception e) {
            logger.error("Error while saving vendor product with id: {}", vendorProductId, e);
            throw e;
        }
    }

    @Override
    public VendorProduct rejectVendorProduct(String vendorProductId) {
        logger.info("Rejecting vendor product with ID: {}", vendorProductId);
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).map(vp -> {
            logger.info("Vendor product found with id: {}", vendorProductId);
            return vp;
        }).orElseThrow(
                () -> {
                    logger.warn("Vendor product not found with id: {}", vendorProductId);
                    return new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!");
                });
        vendorProduct.setApprovalStatus(ApprovalStatus.REJECTED);
        try {
            return vendorProductRepository.save(vendorProduct);
        } catch (Exception e) {
            logger.error("Error while saving vendor product with id: {}", vendorProductId, e);
            throw e;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        logger.info("Fetching all orders");
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderDetails(String orderId) {
        logger.info("Fetching order details for order ID: {}", orderId);
        return orderRepository.findById(orderId).map(o -> {
            logger.info("Order found with id: {}", orderId);
            return o;
        }).orElseThrow(() -> {
            logger.warn("Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order with Id: " + orderId + "not found.");
        });
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        logger.info("Updating order status for order ID: {} to status: {}", orderId, status);
        Order order = getOrderDetails(orderId);
        order.setOrderStatus(status);
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.error("Error while saving order with id: {}", orderId, e);
            throw e;
        }
    }

    @Override
    public void deleteReview(String reviewId) {
        try {
            logger.info("Deleting review with ID: {}", reviewId);
            reviewRepository.deleteById(reviewId);
        } catch (Exception e) {
            logger.error("Error while deleting review with id: {}", reviewId, e);
            throw e;
        }
    }

}
