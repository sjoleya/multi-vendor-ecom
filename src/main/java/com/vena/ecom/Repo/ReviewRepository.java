package com.vena.ecom.repo;

import com.vena.ecom.model.Review;
import com.vena.ecom.model.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review , String> {

    List<Review>findByCustomerCustomerId(String customerId);
    List<Review>findByVendorProductId(String vendorProductId);
    List<Review>findByApprovalStatus(ApprovalStatus status);
    List<Review>findByOrderId(String orderId);

}
