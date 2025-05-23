package com.vena.ecom.service;

import com.vena.ecom.dto.response.OrderItemResponse;
import com.vena.ecom.dto.response.VendorProductResponse;
import com.vena.ecom.dto.response.VendorProfileResponse;
import com.vena.ecom.dto.request.AddVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProductRequest;
import com.vena.ecom.dto.request.UpdateVendorProfileRequest;
import com.vena.ecom.model.enums.ItemStatus;

import java.util.List;

public interface VendorService {

     VendorProfileResponse getVendorProfile(String vendorId);

     VendorProfileResponse getVendorProfileByUserId(String vendorId);

     VendorProfileResponse updateVendorProfile(String vendorId,
               UpdateVendorProfileRequest vendorProfile);

     VendorProductResponse addVendorProduct(String vendorId, AddVendorProductRequest product);

     List<VendorProductResponse> getVendorProducts(String vendorId);

     VendorProductResponse getVendorProductById(String productId);

     VendorProductResponse updateVendorProduct(String productId,
               UpdateVendorProductRequest vendorProduct);

     void deleteVendorProduct(String productId);

     List<OrderItemResponse> getVendorOrderItems(String vendorId);

     OrderItemResponse getVendorOrderItemDetails(String orderItemId);

     OrderItemResponse updateOrderItemStatus(String orderItemId, ItemStatus status);
}
