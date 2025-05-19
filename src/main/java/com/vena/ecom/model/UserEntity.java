package com.vena.ecom.model;

import com.vena.ecom.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;


@Entity
    @Table(name = "Users")
    public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long userId;

    @NotBlank(message = "Enter your first name")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s'-][A-Za-z]+)*$",
            message = "Enter correct name"
    )
    @Size(max = 50)
    private String userFirstName;

    @NotBlank(message = "Enter your Last name")
    @Pattern(
            regexp = "^[A-Za-z]+(?:[\\s'-][A-Za-z]+)*$",
            message = "Enter correct name"
    )
    @Size(max = 50)
    private String userLastName;

    @NotBlank(message = "Enter your email")
    @Email(message = "Email is not valid")
    @Size(max =150)
    @Column(nullable = false,unique = true)
    private String userEmail;


    @NotNull(message = "Enter password")
    @Size(min = 8,message = "Password must be 8 characters")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Role is required")
    private UserRole userRole;

    @Pattern(
            regexp = "^\\\\+?[0-9]{10}$",
            message = "Enter Correct Phone Number"
    )
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updateAtl;

    public UserEntity(Long userId, String userFirstName, String userLastName,
                      String userEmail, String passwordHash, UserRole userRole,
                      String phoneNumber, LocalDateTime createdAt, LocalDateTime updateAtl) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updateAtl = updateAtl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAtl() {
        return updateAtl;
    }

    public void setUpdateAtl(LocalDateTime updateAtl) {
        this.updateAtl = updateAtl;
    }
}
