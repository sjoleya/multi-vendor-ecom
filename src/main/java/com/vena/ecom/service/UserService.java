package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.model.User;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAdressRequest;

public interface UserService {
    UserResponse getCurrentUser();

    UserResponse updateCurrentUser(User userDetails);

    List<AddressResponse> getUserAddresses();

    AddressResponse addUserAddress(AddAdressRequest addAdressRequest);

    AddressResponse updateUserAddress(String addressId, Address addressDetails);

    void deleteUserAddress(String addressId);
}
