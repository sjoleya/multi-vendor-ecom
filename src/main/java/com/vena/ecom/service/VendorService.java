package com.vena.ecom.service;

import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.VendorProduct;
import com.vena.ecom.model.VendorProfile;
import com.vena.ecom.model.enums.ItemStatus;

import java.util.List;

public interface VendorService {

     VendorProfile getVendorProfile(String vendorId);
     VendorProfile updateVendorProfile(String vendorId, VendorProfile
     vendorProfile);

     VendorProduct addVendorProduct(String vendorId, VendorProduct product);
     List<VendorProduct> getVendorProducts(String vendorId);
     VendorProduct getVendorProductById(String productId);
     VendorProduct updateVendorProduct(String productId , VendorProduct vendorProduct);
     void deleteVendorProduct (String productId);

     List<OrderItem> getVendorOrderItems(String vendorId);
     OrderItem getVendorOrderItemDetails(String orderItemId);
     OrderItem updateOrderItemStatus (String orderItemId, ItemStatus status);
}
