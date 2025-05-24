package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

@Entity
@Table(name = "product_catalog")
public class ProductCatalog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String brand;
    private String description;
    private String globalSKU;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ProductCategory category;

    public ProductCatalog(String id, String name, String brand, String description, String globalSKU,
            ProductCategory category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.globalSKU = globalSKU;
        this.category = category;
    }

    public ProductCatalog() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "ProductCatalog{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", globalSKU='" + globalSKU + '\'' +
                ", category=" + category +
                '}';
    }
}
