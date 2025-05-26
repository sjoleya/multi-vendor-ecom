package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;

public interface UserService {
    UserResponse getCurrentUser();

    UserResponse updateCurrentUser(User userDetails);

    List<AddressResponse> getUserAddresses(String userId);

    AddressResponse addUserAddress(String userId, Address address);

    AddressResponse updateUserAddress(String addressId, Address addressDetails);

    void deleteUserAddress(String addressId);
}
