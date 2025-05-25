package com.vena.ecom.dto.response;

import com.vena.ecom.model.Review;
import com.vena.ecom.model.OrderItem;
import com.vena.ecom.model.User;

public class ReviewResponse {
    private String reviewId;
    private String orderItemId;
    private String customerId;
    private int rating;
    private String comment;
    private String createdAt;

    public ReviewResponse() {
    }

    public ReviewResponse(Review review) {
        OrderItem orderItem = review.getOrderItem();
        User customer = review.getCustomer();

        this.reviewId = review.getId();
        this.orderItemId = orderItem.getId();
        this.customerId = customer.getId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt().toString();
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
