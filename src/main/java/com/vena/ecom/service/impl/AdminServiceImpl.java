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
        logger.info("getAllUsers - Fetching all users");
        List<User> users = userRepository.findAll();
        logger.debug("getAllUsers - Found {} users", users.size());
        return users;
    }

    @Override
    public User getUserDetails(String userId) {
        logger.info("getUserDetails - Fetching user details for user ID: {}", userId);
        return userRepository.findById(userId).map(user -> {
            logger.debug("getUserDetails - User found: {}", user);
            return user;
        }).orElseThrow(() -> {
            logger.warn("getUserDetails - User not found with id: {}", userId);
            return new ResourceNotFoundException("User with id: " + userId + "not found!");
        });
    }

    @Override
    public User updateUserRole(String userId, UserRole role) {
        logger.info("updateUserRole - Updating role for user ID: {} to role: {}", userId, role);
        User user = userRepository.findById(userId).map(u -> {
            logger.debug("updateUserRole - User found: {}", u);
            return u;
        }).orElseThrow(() -> {
            logger.warn("updateUserRole - User not found with id: {}", userId);
            return new ResourceNotFoundException("User with id: " + userId + "not found!");
        });
        user.setRole(role);
        try {
            User updatedUser = userRepository.save(user);
            logger.debug("updateUserRole - User role updated: {}", updatedUser);
            return updatedUser;
        } catch (Exception e) {
            logger.error("updateUserRole - Error while saving user with id: {}", userId, e);
            throw e;
        }
    }

    @Override
    public List<VendorProfile> getAllVendorApplications() {
        logger.info("getAllVendorApplications - Fetching all vendor applications");
        List<VendorProfile> vendorProfiles = vendorProfileRepository.findAll();
        List<VendorProfile> pendingApplications = vendorProfiles.stream()
                .filter(profile -> profile.getApprovalStatus().equals(ApprovalStatus.PENDING))
                .toList();
        logger.debug("getAllVendorApplications - Found {} pending vendor applications", pendingApplications.size());
        return pendingApplications;
    }

    @Override
    public VendorProfile approveVendorApplication(String applicationId) {
        logger.info("approveVendorApplication - Approving vendor application with ID: {}", applicationId);
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId).map(vp -> {
            logger.debug("approveVendorApplication - Vendor profile found: {}", vp);
            return vp;
        }).orElseThrow(() -> {
            logger.warn("approveVendorApplication - Vendor profile not found with id: {}", applicationId);
            return new ResourceNotFoundException("Profile with id: " + applicationId + "not found!");
        });
        vendorProfile.setApprovalStatus(ApprovalStatus.APPROVED);
        try {
            VendorProfile approvedProfile = vendorProfileRepository.save(vendorProfile);
            logger.debug("approveVendorApplication - Vendor application approved: {}", approvedProfile);
            return approvedProfile;
        } catch (Exception e) {
            logger.error("approveVendorApplication - Error while saving vendor profile with id: {}", applicationId, e);
            throw e;
        }
    }

    @Override
    public VendorProfile rejectVendorApplication(String applicationId) {
        logger.info("rejectVendorApplication - Rejecting vendor application with ID: {}", applicationId);
        VendorProfile vendorProfile = vendorProfileRepository.findById(applicationId).map(vp -> {
            logger.debug("rejectVendorApplication - Vendor profile found: {}", vp);
            return vp;
        }).orElseThrow(() -> {
            logger.warn("rejectVendorApplication - Vendor profile not found with id: {}", applicationId);
            return new ResourceNotFoundException("Profile with id: " + applicationId + "not found!");
        });
        vendorProfile.setApprovalStatus(ApprovalStatus.REJECTED);
        try {
            VendorProfile rejectedProfile = vendorProfileRepository.save(vendorProfile);
            logger.debug("rejectVendorApplication - Vendor application rejected: {}", rejectedProfile);
            return rejectedProfile;
        } catch (Exception e) {
            logger.error("rejectVendorApplication - Error while saving vendor profile with id: {}", applicationId, e);
            throw e;
        }
    }

    @Override
    public List<VendorProduct> getPendingVendorProductApprovals() {
        logger.info("getPendingVendorProductApprovals - Fetching pending vendor product approvals");
        List<VendorProduct> vendorProducts = vendorProductRepository.findAll();
        List<VendorProduct> pendingProducts = vendorProducts.stream()
                .filter(product -> product.getApprovalStatus().equals(ApprovalStatus.PENDING)).toList();
        logger.debug("getPendingVendorProductApprovals - Found {} pending vendor products", pendingProducts.size());
        return pendingProducts;
    }

    @Override
    public VendorProduct approveVendorProduct(String vendorProductId) {
        logger.info("approveVendorProduct - Approving vendor product with ID: {}", vendorProductId);
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).map(vp -> {
            logger.debug("approveVendorProduct - Vendor product found: {}", vp);
            return vp;
        }).orElseThrow(
                () -> {
                    logger.warn("approveVendorProduct - Vendor product not found with id: {}", vendorProductId);
                    return new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!");
                });
        vendorProduct.setApprovalStatus(ApprovalStatus.APPROVED);
        try {
            VendorProduct approvedProduct = vendorProductRepository.save(vendorProduct);
            logger.debug("approveVendorProduct - Vendor product approved: {}", approvedProduct);
            return approvedProduct;
        } catch (Exception e) {
            logger.error("approveVendorProduct - Error while saving vendor product with id: {}", vendorProductId, e);
            throw e;
        }
    }

    @Override
    public VendorProduct rejectVendorProduct(String vendorProductId) {
        logger.info("rejectVendorProduct - Rejecting vendor product with ID: {}", vendorProductId);
        VendorProduct vendorProduct = vendorProductRepository.findById(vendorProductId).map(vp -> {
            logger.debug("rejectVendorProduct - Vendor product found: {}", vp);
            return vp;
        }).orElseThrow(
                () -> {
                    logger.warn("rejectVendorProduct - Vendor product not found with id: {}", vendorProductId);
                    return new ResourceNotFoundException("Vendor Product with Id: " + vendorProductId + "not found!");
                });
        vendorProduct.setApprovalStatus(ApprovalStatus.REJECTED);
        try {
            VendorProduct rejectedProduct = vendorProductRepository.save(vendorProduct);
            logger.debug("rejectVendorProduct - Vendor product rejected: {}", rejectedProduct);
            return rejectedProduct;
        } catch (Exception e) {
            logger.error("rejectVendorProduct - Error while saving vendor product with id: {}", vendorProductId, e);
            throw e;
        }
    }

    @Override
    public List<Order> getAllOrders() {
        logger.info("getAllOrders - Fetching all orders");
        List<Order> orders = orderRepository.findAll();
        logger.debug("getAllOrders - Found {} orders", orders.size());
        return orders;
    }

    @Override
    public Order getOrderDetails(String orderId) {
        logger.info("getOrderDetails - Fetching order details for order ID: {}", orderId);
        return orderRepository.findById(orderId).map(o -> {
            logger.debug("getOrderDetails - Order found: {}", o);
            return o;
        }).orElseThrow(() -> {
            logger.warn("getOrderDetails - Order not found with id: {}", orderId);
            return new ResourceNotFoundException("Order with Id: " + orderId + "not found.");
        });
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus status) {
        logger.info("updateOrderStatus - Updating order status for order ID: {} to status: {}", orderId, status);
        Order order = getOrderDetails(orderId);
        order.setOrderStatus(status);
        try {
            Order updatedOrder = orderRepository.save(order);
            logger.debug("updateOrderStatus - Order status updated: {}", updatedOrder);
            return updatedOrder;
        } catch (Exception e) {
            logger.error("updateOrderStatus - Error while saving order with id: {}", orderId, e);
            throw e;
        }
    }

    @Override
    public void deleteReview(String reviewId) {
        try {
            logger.info("deleteReview - Deleting review with ID: {}", reviewId);
            reviewRepository.deleteById(reviewId);
            logger.debug("deleteReview - Review with ID: {} deleted successfully", reviewId);
        } catch (Exception e) {
            logger.error("deleteReview - Error while deleting review with id: {}", reviewId, e);
            throw e;
        }
    }

}
