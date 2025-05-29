package com.vena.ecom.service;

import java.util.List;

import com.vena.ecom.dto.request.UserRequest;
import com.vena.ecom.dto.response.AddressResponse;
import com.vena.ecom.dto.response.UserResponse;
import com.vena.ecom.model.User;
import com.vena.ecom.model.Address;
import com.vena.ecom.dto.request.AddAddressRequest;

public interface UserService {




    UserResponse createUser(UserRequest request);

    UserResponse getCurrentUser(String id);

    UserResponse updateCurrentUser(User userDetails);

    List<AddressResponse> getUserAddresses(String userId);

    AddressResponse addUserAddress(AddAddressRequest addAdressRequest,String userId);

    AddressResponse updateUserAddress(String addressId, Address addressDetails);

    void deleteUserAddress(String addressId);
}
