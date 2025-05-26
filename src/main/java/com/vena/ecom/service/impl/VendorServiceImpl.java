package com.vena.ecom.service.impl;

import com.vena.ecom.dto.request.UpdateVendorProductRequest;
import com.vena.ecom.dto.request.AddVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProfileRequest;
import com.vena.ecom.exception.ResourceNotFoundException;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.ApprovalStatus;
import com.vena.ecom.model.enums.ItemStatus;
import com.vena.ecom.repo.OrderItemRepository;
import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.repo.VendorProfileRepository;
import com.vena.ecom.service.VendorService;
import com.vena.ecom.repo.ProductCatalogRepository;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.OrderItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

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

    @Override
    public VendorProfileResponse getVendorProfile(String vendorProfileId) {
       logger.info("Fetching VendorProfile by ID: {}",vendorProfileId);
        VendorProfile profile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with id: {}", vendorProfileId);
                    return new RuntimeException("VendorProfile not found with id: " + vendorProfileId);
                });
        logger.debug("VendorProfile found: {}", profile.getId());
        return toVendorProfileResponse(profile);
    }

    public VendorProfileResponse getVendorProfileByUserId(String userId) {
        logger.info("Fetching VendorProfile by User ID: {}", userId);
        VendorProfile profile = vendorProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> {
                    logger.error("VendorProfile not found with User ID: {}", userId);
                    return new ResourceNotFoundException("VendorProfile not found with id: " + userId);
                });
        return toVendorProfileResponse(profile);
    }

    @Override
    public VendorProfileResponse updateVendorProfile(String vendorProfileId,
            UpdateVendorProfileRequest updatedProfile) {
        logger.info("Updating VendorProfile with ID: {}", vendorProfileId);
        VendorProfile existingProfile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() ->  {
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
        return toVendorProfileResponse(saved);
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
        vendorProduct.setVendorId(vendor);
        vendorProduct.setCatalogProductId(productCatalogRepository.findById(product.getCatalogProductId())
                .orElseThrow(() -> {
                    logger.error("Product Catalog not found for ID: {}", product.getCatalogProductId());
                    return new ResourceNotFoundException("Product Catalog not found");
                })
        );
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
        return toVendorProductResponse(saved);
    }

    @Override
    public List<VendorProductResponse> getVendorProducts(String vendorId) {
        logger.info("Fetching products for Vendor ID: {}", vendorId);
        return vendorProductRepository.findByVendorId_Id(vendorId)
                .stream().map(this::toVendorProductResponse).toList();
    }

    @Override
    public VendorProductResponse getVendorProductById(String productId) {
        logger.info("Fetching VendorProduct by ID: {}", productId);
        VendorProduct product = vendorProductRepository.findById(productId)
                .orElseThrow(() ->{
                    logger.error("VendorProduct not found with id: {}", productId);
                    return new ResourceNotFoundException("VendorProduct not found with id: " + productId);
                });
        return toVendorProductResponse(product);
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
        return toVendorProductResponse(saved);
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
        return orderItemRepository.findByVendorProduct_VendorId_Id(vendorId)
                .stream().map(this::toOrderItemResponse).toList();
    }

    @Override
    public OrderItemResponse getVendorOrderItemDetails(String orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + orderItemId));
        return toOrderItemResponse(orderItem);
    }

    @Override
    public OrderItemResponse updateOrderItemStatus(String orderItemId, ItemStatus status) {
        logger.info("Fetching OrderItem details for ID: {}", orderItemId);
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() ->{
                    logger.error("OrderItem not found with ID: {}", orderItemId);
                    return new ResourceNotFoundException("OrderItem not found with id: " + orderItemId);
                });
        logger.debug("Current OrderItem status: {}", orderItem.getItemStatus());
        logger.debug("Updating OrderItem status to: {}", status);
        orderItem.setItemStatus(status);
        OrderItem saved = orderItemRepository.save(orderItem);
        logger.info("OrderItem status updated successfully for ID: {}", saved.getId());
        return toOrderItemResponse(saved);
    }

    private VendorProfileResponse toVendorProfileResponse(VendorProfile profile) {

        VendorProfileResponse dto = new VendorProfileResponse();
        dto.vendorProfileId = profile.getId();
        dto.userId = profile.getUser() != null ? profile.getUser().getId() : null;
        dto.storeName = profile.getStoreName();
        dto.status = profile.getApprovalStatus() != null ? profile.getApprovalStatus().name() : null;
        return dto;
    }

    private VendorProductResponse toVendorProductResponse(VendorProduct product) {
        VendorProductResponse dto = new VendorProductResponse();
        dto.vendorProductId = product.getId();
        dto.vendorId = product.getVendorId() != null ? product.getVendorId().getId() : null;
        dto.catalogProductId = product.getCatalogProductId() != null ? product.getCatalogProductId().getId() : null;
        dto.price = product.getPrice() != null ? product.getPrice().doubleValue() : 0.0;
        dto.stock = product.getStockQuantity() != null ? product.getStockQuantity() : 0;
        dto.status = product.getApprovalStatus() != null ? product.getApprovalStatus().name() : null;
        return dto;
    }

    private OrderItemResponse toOrderItemResponse(OrderItem item) {
        OrderItemResponse dto = new OrderItemResponse();
        dto.orderItemId = item.getId();
        dto.productId = item.getVendorProduct() != null ? item.getVendorProduct().getId() : null;
        dto.productName = item.getVendorProduct() != null ? item.getVendorProduct().getName() : null;
        dto.quantity = item.getQuantity() != null ? item.getQuantity() : 0;
        dto.price = item.getPriceAtPurchase() != null ? item.getPriceAtPurchase().doubleValue() : 0.0;
        dto.status = item.getItemStatus() != null ? item.getItemStatus().name() : null;
        dto.vendorId = item.getVendorProduct() != null && item.getVendorProduct().getVendorId() != null
                ? item.getVendorProduct().getVendorId().getId()
                : null;
        return dto;
    }
}
