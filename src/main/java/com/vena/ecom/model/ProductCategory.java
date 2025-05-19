package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity

public class ProductCategory extends Auditable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long categoryId;

private String name;
private String description;

private LocalDateTime createdAt;
private LocalDateTime updatedAt;

@PrePersist
    protected void onCreate(){
    this.createdAt = LocalDateTime.now();
    this.updatedAt = this.createdAt;
}

@PrePersist
    protected void onUpdate(){
    this.updatedAt = LocalDateTime.now();
}

//Constructors


    public ProductCategory() {
    }

    public ProductCategory(Long categoryId, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    //Getter and Setters


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
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

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    //to String()

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
