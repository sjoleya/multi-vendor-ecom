package com.vena.ecom.dto.response;

import java.util.List;

public class UserResponse {
    public String id;
    public String name;
    public String email;
    public String role;
    public java.util.List<AddressResponse> addresses;

    public UserResponse() {
    }

    public UserResponse(String id, String name, String email, String role, List<AddressResponse> addresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.addresses = addresses;
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
