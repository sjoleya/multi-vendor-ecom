package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "productcatalogs")
public class ProductCatalog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String catalogId;
    private String name;
    private String brand;
    private String description;
    private String globalSKU;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private ProductCategory categoryId;


    public ProductCatalog(String catalogId, String name, String brand, String description, String globalSKU,
            ProductCategory categoryId) {
        this.catalogId = catalogId;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.globalSKU = globalSKU;
        this.categoryId = categoryId;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGlobalSKU() {
        return globalSKU;
    }

    public void setGlobalSKU(String globalSKU) {
        this.globalSKU = globalSKU;
    }

    public ProductCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(ProductCategory categoryId) {
        this.categoryId = categoryId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
