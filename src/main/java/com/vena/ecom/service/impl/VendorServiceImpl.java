package com.vena.ecom.service.impl;

import com.vena.ecom.dto.UpdateVendorProductRequest;
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
    public VendorProfile getVendorProfile(String vendorProfileId) {
        return vendorProfileRepository.findById(vendorProfileId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + vendorProfileId));
    }

    public VendorProfile getVendorProfileByUserId(String userId) {
        return vendorProfileRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + userId));
    }

    @Override
    public VendorProfile updateVendorProfile(String vendorProfileId,
            com.vena.ecom.dto.UpdateVendorProfileRequest updatedProfile) {
        VendorProfile existingProfile = getVendorProfile(vendorProfileId);
        if (updatedProfile.getStoreName() != null) {
            existingProfile.setStoreName(updatedProfile.getStoreName());
        }
        if (updatedProfile.getStoreDescription() != null) {
            existingProfile.setStoreDescription(updatedProfile.getStoreDescription());
        }
        if (updatedProfile.getContactNumber() != null) {
            existingProfile.setContactNumber(updatedProfile.getContactNumber());
        }
        return vendorProfileRepository.save(existingProfile);
    }

    @Override
    public VendorProduct addVendorProduct(String vendorId, com.vena.ecom.dto.AddVendorProductRequest product) {
        VendorProfile vendor = getVendorProfile(vendorId);
        VendorProduct vendorProduct = new VendorProduct();
        vendorProduct.setVendorId(vendor);
        vendorProduct.setCatalogProductId(productCatalogRepository.findById(product.getCatalogProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Catalog not found")));
        vendorProduct.setSKU(product.getSku());
        vendorProduct.setPrice(product.getPrice());
        vendorProduct.setName(product.getName());
        vendorProduct.setDescription(product.getDescription());
        vendorProduct.setStockQuantity(product.getStockQuantity());
        vendorProduct.setApprovalStatus(ApprovalStatus.PENDING);
        vendorProduct.setActive(false);
        return vendorProductRepository.save(vendorProduct);
    }

    @Override
    public List<VendorProduct> getVendorProducts(String vendorId) {
        return vendorProductRepository.findByVendorId_Id(vendorId);
    }

    @Override
    public VendorProduct getVendorProductById(String productId) {
        return vendorProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("VendorProduct not found with id: " + productId));
    }

    @Override
    public VendorProduct updateVendorProduct(String productId, UpdateVendorProductRequest updatedProduct) {
        VendorProduct existingProduct = getVendorProductById(productId);
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

        return vendorProductRepository.save(existingProduct);

    }

    @Override
    public void deleteVendorProduct(String productId) {
        vendorProductRepository.deleteById(productId);
    }

    @Override
    public List<OrderItem> getVendorOrderItems(String vendorId) {
        return orderItemRepository.findByVendorProduct_VendorId_Id(vendorId);
    }

    @Override
    public OrderItem getVendorOrderItemDetails(String orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem not found with id: " + orderItemId));
    }

    @Override
    public OrderItem updateOrderItemStatus(String orderItemId, ItemStatus status) {
        OrderItem orderItem = getVendorOrderItemDetails(orderItemId);
        orderItem.setItemStatus(status);
        return orderItemRepository.save(orderItem);
    }
}
