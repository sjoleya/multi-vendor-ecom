package com.vena.ecom.dto.response;

import com.vena.ecom.model.User;

import java.util.List;

public class UserResponse {
    private String userId;
    private String name;
    private String email;
    private String role;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private List<AddressResponse> addresses;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.userId = user.getId();
        this.name = user.getFirstName() + " " + user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.phoneNumber = user.getPhoneNumber();
        this.addresses = user.getAddressList().stream()
                .map(AddressResponse::new)
                .toList();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressResponse> addresses) {
        this.addresses = addresses;
    }
}
