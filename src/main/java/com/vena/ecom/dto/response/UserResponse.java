package com.vena.ecom.dto.response;

import com.vena.ecom.model.User;
import com.vena.ecom.model.enums.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private String userId;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
    private List<AddressResponse> addresses;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.firstname = user.getFirstName();
        this.lastname = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole();


        if (user.getAddressList() != null) {
            this.addresses = user.getAddressList()
                    .stream()
                    .map(AddressResponse::new)
                    .collect(Collectors.toList());
        } else {
            this.addresses = Collections.emptyList();
        }
    }


    public String getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getRole() {
        return role;
    }

    public List<AddressResponse> getAddresses() {
        return addresses;
    }
}
