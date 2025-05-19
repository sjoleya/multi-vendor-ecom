package com.vena.ecom.model;

import com.vena.ecom.model.audit.Auditable;
import com.vena.ecom.model.enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String phoneNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

    public User(String userId, String firstName, String lastName, String email, String passwordHash,
            UserRole role,
            String phoneNumber) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
}