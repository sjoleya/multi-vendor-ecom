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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

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
        VendorProfile profile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + vendorProfileId));
        return new VendorProfileResponse(profile);
    }

    public VendorProfileResponse getVendorProfileByUserId(String userId) {
        VendorProfile profile = vendorProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + userId));
        return new VendorProfileResponse(profile);
    }

    @Override
    public VendorProfileResponse updateVendorProfile(String vendorProfileId,
            UpdateVendorProfileRequest updatedProfile) {
        VendorProfile existingProfile = vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + vendorProfileId));
        if (updatedProfile.getStoreName() != null) {
            existingProfile.setStoreName(updatedProfile.getStoreName());
        }
        if (updatedProfile.getStoreDescription() != null) {
            existingProfile.setStoreDescription(updatedProfile.getStoreDescription());
        }
        if (updatedProfile.getContactNumber() != null) {
            existingProfile.setContactNumber(updatedProfile.getContactNumber());
        }
        VendorProfile saved = vendorProfileRepository.save(existingProfile);
        return new VendorProfileResponse(saved);
    }

    @Override
    public VendorProductResponse addVendorProduct(String vendorId, AddVendorProductRequest product) {
        VendorProfile vendor = vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProfile not found with id: " + vendorId));
        VendorProduct vendorProduct = new VendorProduct();
        vendorProduct.setVendorId(vendor);
        vendorProduct.setProductCatalog(productCatalogRepository.findById(product.getCatalogProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Catalog not found")));
        vendorProduct.setSKU(product.getSku());
        vendorProduct.setPrice(product.getPrice());
        vendorProduct.setName(product.getName());
        vendorProduct.setDescription(product.getDescription());
        vendorProduct.setStockQuantity(product.getStockQuantity());
        vendorProduct.setApprovalStatus(ApprovalStatus.PENDING);
        vendorProduct.setActive(false);
        VendorProduct saved = vendorProductRepository.save(vendorProduct);
        return new VendorProductResponse(saved);
    }

    @Override
    public List<VendorProductResponse> getVendorProducts(String vendorId) {
        return vendorProductRepository.findByVendorId_Id(vendorId)
                .stream().map(VendorProductResponse::new).toList();
    }

    @Override
    public VendorProductResponse getVendorProductById(String productId) {
        VendorProduct product = vendorProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProduct not found with id: " + productId));
        return new VendorProductResponse(product);
    }

    @Override
    public VendorProductResponse updateVendorProduct(String productId, UpdateVendorProductRequest updatedProduct) {
        VendorProduct existingProduct = vendorProductRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProduct not found with id: " + productId));
        if (updatedProduct.getPrice() != null) {
            existingProduct.setPrice(updatedProduct.getPrice());
        }
        if (updatedProduct.getQuantity() != null) {
            existingProduct.setStockQuantity(updatedProduct.getQuantity());
        }
        if (updatedProduct.getName() != null) {
            existingProduct.setName(updatedProduct.getName());
        }
        if (updatedProduct.getDescription() != null) {
            existingProduct.setDescription(updatedProduct.getDescription());
        }
        if (updatedProduct.getIsActive() != null) {
            existingProduct.setActive(updatedProduct.getIsActive());
        }
        VendorProduct saved = vendorProductRepository.save(existingProduct);
        return new VendorProductResponse(saved);
    }

    @Override
    public void deleteVendorProduct(String productId) {
        vendorProductRepository.deleteById(productId);
    }

    @Override
    public List<OrderItemResponse> getVendorOrderItems(String vendorId) {
        return orderItemRepository.findByVendorProduct_VendorId_Id(vendorId)
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
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + orderItemId));
        orderItem.setItemStatus(status);
        OrderItem saved = orderItemRepository.save(orderItem);
        return new OrderItemResponse(saved);
    }
}
