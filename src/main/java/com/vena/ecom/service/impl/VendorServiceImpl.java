package com.vena.ecom.service.impl;

import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.ItemStatus;
//import com.vena.ecom.repo.OrderItemRepository;
//import com.vena.ecom.repo.VendorProductRepository;
import com.vena.ecom.repo.VendorProfileRepository;
import com.vena.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorServiceImpl implements VendorService {

    @Autowired
    private VendorProfileRepository vendorProfileRepository;

    @Autowired
    private VendorProductRepository vendorProductRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public VendorProfile getVendorProfile(String vendorId) {
        return vendorProfileRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("VendorProfile not found with id: " + vendorId));
    }

    @Override
    public VendorProfile updateVendorProfile(String vendorId, VendorProfile updatedProfile) {
        VendorProfile existingProfile = getVendorProfile(vendorId);
        existingProfile.setStoreName(updatedProfile.getStoreName());
        existingProfile.setStoreDescription(updatedProfile.getStoreDescription());
        existingProfile.setBusinessAddress(updatedProfile.getBusinessAddress());
        existingProfile.setContactNumber(updatedProfile.getContactNumber());
        existingProfile.setApprovalStatus(updatedProfile.getApprovalStatus());
        return vendorProfileRepository.save(existingProfile);
    }

    @Override
    public VendorProduct addVendorProduct(String vendorId, VendorProduct product) {
        VendorProfile vendor = getVendorProfile(vendorId);
        product.setVendorProfile(vendor);
        return vendorProductRepository.save(product);
    }

    @Override
    public List<VendorProduct> getVendorProducts(String vendorId) {
        return vendorProductRepository.findByVendorProfileVendorId(vendorId);
    }

    @Override
    public VendorProduct getVendorProductById(String productId) {
        return vendorProductRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("VendorProduct not found with id: " + productId));
    }

    @Override
    public VendorProduct updateVendorProduct(String productId, VendorProduct updatedProduct) {
        VendorProduct existingProduct = getVendorProductById(productId);
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategory(updatedProduct.getCategory());
        return vendorProductRepository.save(existingProduct);
    }

    @Override
    public void deleteVendorProduct(String productId) {
        vendorProductRepository.deleteById(productId);
    }

    @Override
    public List<OrderItem> getVendorOrderItems(String vendorId) {
        return orderItemRepository.findByVendorProduct_VendorProfile_VendorId(vendorId);
    }

    @Override
    public OrderItem getVendorOrderItemDetails(String orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + orderItemId));
    }

    @Override
    public OrderItem updateOrderItemStatus(String orderItemId, ItemStatus status) {
        OrderItem orderItem = getVendorOrderItemDetails(orderItemId);
        orderItem.setItemStatus(status);
        return orderItemRepository.save(orderItem);
    }
}
