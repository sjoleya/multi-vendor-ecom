package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class ProductCategory extends Auditable {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private String categoryId;

private String name;
private String description;

//Constructors
    public ProductCategory() {
    }

    public ProductCategory(String categoryId, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;

    }

    //Getter and Setters
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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

}
