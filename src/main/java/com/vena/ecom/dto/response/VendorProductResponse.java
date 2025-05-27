package com.vena.ecom.dto.response;

import com.vena.ecom.model.VendorProduct;

import java.math.BigDecimal;

public class VendorProductResponse {
    private String vendorProductId;
    private String vendorId;
    private String catalogProductId;
    private BigDecimal price;
    private int stock;
    private String status;

    public VendorProductResponse() {
    }

    public VendorProductResponse(VendorProduct vendorProduct) {
        this.vendorProductId = vendorProduct.getId();
        this.vendorId = vendorProduct.getVendorProfile().getId();
        this.catalogProductId = vendorProduct.getProductCatalog().getId();
        this.price = vendorProduct.getPrice();
        this.stock = vendorProduct.getStockQuantity();
        this.status = vendorProduct.getApprovalStatus().toString();
    }

    public String getVendorProductId() {
        return vendorProductId;
    }

    public void setVendorProductId(String vendorProductId) {
        this.vendorProductId = vendorProductId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCatalogProductId() {
        return catalogProductId;
    }

    public void setCatalogProductId(String catalogProductId) {
        this.catalogProductId = catalogProductId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
