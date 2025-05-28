package com.vena.ecom.dto.response;

import com.vena.ecom.model.User;
import com.vena.ecom.model.enums.UserRole;

import java.util.List;

public class NewUserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String phoneNumber;
    private List<AddressResponse> addresses;

    public NewUserResponse() {
    }

    public NewUserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.role = UserRole.valueOf(user.getRole().toString());
        this.addresses = user.getAddressList().stream()
                .map(AddressResponse::new)
                .toList();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
