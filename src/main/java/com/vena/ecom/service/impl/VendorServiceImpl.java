package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.UpdateVendorProductRequest;
import com.vena.ecom.dto.request.AddVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProfileRequest;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.User;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.ItemStatus;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.repo.VendorProfileRepository;
import com.vena.ecom.service.VendorService;
import com.vena.ecom.repo.ProductCatalogRepository;
import com.vena.ecom.repo.UserRepository;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.vena.ecom.dto.request.AddVendorProfileRequest;
import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {
    private static final Logger logger = LoggerFactory.getLogger(VendorServiceImpl.class);

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public VendorProfileResponse getVendorProfile(String vendorProfileId) {
        logger.info("Fetching VendorProfile by ID: {}", vendorProfileId);
        VendorProfile profile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with id: {}", vendorProfileId);
                    return new RuntimeException("VendorProfile not found with id: " + vendorProfileId);
                });
        logger.debug("VendorProfile found: {}", profile.getId());
        return new VendorProfileResponse(profile);
    }

    public VendorProfileResponse getVendorProfileByUserId(String userId) {
        logger.info("Fetching VendorProfile by User ID: {}", userId);
        VendorProfile profile = vendorProfileRepository.findByVendorId(userId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with User ID: {}", userId);
                    return new ResourceNotFoundException("VendorProfile not found with id: " + userId);
                });
        return new VendorProfileResponse(profile);
    }

    @Override
    public VendorProfileResponse updateVendorProfile(String vendorProfileId,
            UpdateVendorProfileRequest updatedProfile) {
        logger.info("Updating VendorProfile with ID: {}", vendorProfileId);
        VendorProfile existingProfile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with ID: {}", vendorProfileId);
                    return new ResourceNotFoundException("VendorProfile not found with id: " + vendorProfileId);
                });

        logger.debug("Existing VendorProfile before update: {}", existingProfile);

        if (updatedProfile.getStoreName() != null) {
            logger.debug("Updating storeName to: {}", updatedProfile.getStoreName());
            existingProfile.setStoreName(updatedProfile.getStoreName());
        }
        if (updatedProfile.getStoreDescription() != null) {
            logger.debug("Updating storeDescription to: {}", updatedProfile.getStoreDescription());
            existingProfile.setStoreDescription(updatedProfile.getStoreDescription());
        }
        if (updatedProfile.getContactNumber() != null) {
            logger.debug("Updating contactNumber to: {}", updatedProfile.getContactNumber());
            existingProfile.setContactNumber(updatedProfile.getContactNumber());
        }
        VendorProfile saved = vendorProfileRepository.save(existingProfile);
        return new VendorProfileResponse(saved);
    }

    @Override
    public VendorProductResponse addVendorProduct(String vendorId, AddVendorProductRequest product) {
        logger.info("Adding new VendorProduct for Vendor ID: {}", vendorId);
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with ID: {}", vendorId);
                    return new ResourceNotFoundException("VendorProfile not found with id: " + vendorId);
                });
        logger.debug("VendorProfile found: {}", vendor.getId());
        VendorProduct vendorProduct = new VendorProduct();
        vendorProduct.setVendorProfile(vendor);
        vendorProduct.setProductCatalog(productCatalogRepository.findById(product.getCatalogProductId())
                .orElseThrow(() -> {
                    logger.error("Product Catalog not found for ID: {}", product.getCatalogProductId());
                    return new ResourceNotFoundException("Product Catalog not found");
                }));
        vendorProduct.setSKU(product.getSku());
        vendorProduct.setPrice(product.getPrice());
        vendorProduct.setName(product.getName());
        vendorProduct.setDescription(product.getDescription());
        vendorProduct.setStockQuantity(product.getStockQuantity());
        vendorProduct.setApprovalStatus(ApprovalStatus.PENDING);
        vendorProduct.setActive(false);
        logger.debug("Prepared VendorProduct: {}", vendorProduct);
        VendorProduct saved = vendorProductRepository.save(vendorProduct);
        logger.info("VendorProduct saved with ID: {}", saved.getId());
        return new VendorProductResponse(saved);
    }

    @Override
    public List<VendorProductResponse> getVendorProducts(String vendorId) {
        logger.info("Fetching products for Vendor ID: {}", vendorId);
        return vendorProductRepository.findByVendorProfileId(vendorId)
                .stream().map(VendorProductResponse::new).toList();
    }

    @Override
    public VendorProductResponse getVendorProductById(String productId) {
        logger.info("Fetching VendorProduct by ID: {}", productId);
        VendorProduct product = vendorProductRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("VendorProduct not found with id: {}", productId);
                    return new ResourceNotFoundException("VendorProduct not found with id: " + productId);
                });
        return new VendorProductResponse(product);
    }

    @Override
    public VendorProductResponse updateVendorProduct(String productId, UpdateVendorProductRequest updatedProduct) {
        logger.info("Updating VendorProduct with ID: {}", productId);
        VendorProduct existingProduct = vendorProductRepository.findById(productId)
                .orElseThrow(() -> {
                    logger.error("VendorProduct not found with id: {}", productId);
                    return new ResourceNotFoundException("VendorProduct not found with id: " + productId);
                });
        logger.debug("Existing VendorProduct before update: {}", existingProduct);
        if (updatedProduct.getPrice() != null) {
            logger.debug("Updating price to: {}", updatedProduct.getPrice());
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getQuantity() != null) {
            logger.debug("Updating stock quantity to: {}", updatedProduct.getQuantity());
            existingProduct.setStockQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.getName() != null) {
            logger.debug("Updating name to: {}", updatedProduct.getName());
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            logger.debug("Updating description to: {}", updatedProduct.getDescription());
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getIsActive() != null) {
            logger.debug("Updating active status to: {}", updatedProduct.getIsActive());
            existingProduct.setActive(updatedProduct.getIsActive());
        }
        VendorProduct saved = vendorProductRepository.save(existingProduct);
        logger.info("Successfully updated VendorProduct with ID: {}", saved.getId());
        return new VendorProductResponse(saved);
    }

    @Override
    public void deleteVendorProduct(String productId) {
        logger.warn("Deleting VendorProduct with ID: {}", productId);
        vendorProductRepository.deleteById(productId);
        logger.info("VendorProduct deleted successfully");
    }

    @Override
    public List<OrderItemResponse> getVendorOrderItems(String vendorId) {
        logger.info("Fetching order items for Vendor ID: {}", vendorId);
        return orderItemRepository.findByVendorProductVendorProfileId(vendorId)
                .stream().map(OrderItemResponse::new).toList();
    }

    @Override
    public OrderItemResponse getVendorOrderItemDetails(String orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + orderItemId));
        return new OrderItemResponse(orderItem);
    }

    @Override
    public OrderItemResponse updateOrderItemStatus(String orderItemId, ItemStatus status) {
        logger.info("Fetching OrderItem details for ID: {}", orderItemId);
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> {
                    logger.error("OrderItem not found with ID: {}", orderItemId);
                    return new ResourceNotFoundException("OrderItem not found with id: " + orderItemId);
                });
        logger.debug("Current OrderItem status: {}", orderItem.getItemStatus());
        logger.debug("Updating OrderItem status to: {}", status);
        orderItem.setItemStatus(status);
        OrderItem saved = orderItemRepository.save(orderItem);
        logger.info("OrderItem status updated successfully for ID: {}", saved.getId());
        return new OrderItemResponse(saved);
    }

    @Override
    public VendorProfileResponse createVendorProfile(AddVendorProfileRequest addVendorProfileRequest) {
        String userId = addVendorProfileRequest.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (vendorProfileRepository.existsByVendor(user)) {
            throw new IllegalArgumentException("Vendor Profile for this user already exists");
        }
        logger.info("Creating VendorProfile for User ID: {}", userId);
        VendorProfile vendorProfile = new VendorProfile();
        vendorProfile.setVendor(user);
        vendorProfile.setStoreName(addVendorProfileRequest.getShopName());
        vendorProfile.setStoreDescription(addVendorProfileRequest.getDescription());
        vendorProfile.setContactNumber(addVendorProfileRequest.getContactNumber());
        VendorProfile saved = vendorProfileRepository.save(vendorProfile);
        return new VendorProfileResponse(saved);
    }
}
